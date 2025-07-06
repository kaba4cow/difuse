package com.kaba4cow.difuse.core.system.bean;

import java.util.Set;

import com.kaba4cow.difuse.core.DifuseApplication;
import com.kaba4cow.difuse.core.DifuseException;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.context.ContextScanner;
import com.kaba4cow.difuse.core.context.source.ContextSource;
import com.kaba4cow.difuse.core.context.source.support.ContextSourceRegistry;
import com.kaba4cow.difuse.core.system.bean.registry.impl.InternalSystemBeanRegistry;
import com.kaba4cow.difuse.core.system.shutdownhook.SystemShutdownHookRegistrar;
import com.kaba4cow.difuse.core.util.reflections.ConstructorScanner;

public class SystemBeanRegistrar {

	private final ContextScanner contextScanner;

	private final ContextSourceRegistry contextSourceRegistry;

	private final InternalSystemBeanRegistry internalBeanRegistry;

	private final SystemShutdownHookRegistrar shutdownHookRegistrar;

	public SystemBeanRegistrar(ContextScanner contextScanner, ContextSourceRegistry contextSourceRegistry,
			InternalSystemBeanRegistry internalBeanRegistry, SystemShutdownHookRegistrar shutdownHookRegistrar) {
		this.contextScanner = contextScanner;
		this.contextSourceRegistry = contextSourceRegistry;
		this.internalBeanRegistry = internalBeanRegistry;
		this.shutdownHookRegistrar = shutdownHookRegistrar;
	}

	public void registerBeans() {
		registerBeans(DifuseApplication.class);
		for (ContextSource contextSource : contextSourceRegistry.getContextSources())
			registerBeans(contextSource.getSourceClass());
	}

	private void registerBeans(Class<?> sourceClass) {
		Set<Class<?>> beanClasses = contextScanner.getPackageScanner(sourceClass).searchClassesAnnotatedWith(SystemBean.class);
		for (Class<?> beanClass : beanClasses)
			registerBean(beanClass);
	}

	public <T> T registerBean(Class<T> type) {
		return registerBean(createInstance(type));
	}

	public <T> T registerBean(T bean) {
		internalBeanRegistry.registerBean(bean);
		shutdownHookRegistrar.registerShutdownHooks(bean);
		return bean;
	}

	private <T> T createInstance(Class<T> type) {
		try {
			Object instance = ConstructorScanner.of(type).findNoArgsConstructor().newInstance();
			return type.cast(instance);
		} catch (Exception exception) {
			throw new DifuseException(String.format("Could not create SystemBean %s", type), exception);
		}
	}

}
