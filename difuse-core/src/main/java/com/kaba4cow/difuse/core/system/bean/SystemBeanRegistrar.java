package com.kaba4cow.difuse.core.system.bean;

import java.util.Set;

import com.kaba4cow.difuse.core.DifuseApplication;
import com.kaba4cow.difuse.core.DifuseException;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.system.bean.registry.impl.InternalSystemBeanRegistry;
import com.kaba4cow.difuse.core.system.shutdownhook.SystemShutdownHookRegistrar;
import com.kaba4cow.difuse.core.util.reflections.ConstructorScanner;
import com.kaba4cow.difuse.core.util.reflections.PackageScanner;

public class SystemBeanRegistrar {

	private final InternalSystemBeanRegistry internalBeanRegistry;

	private final SystemShutdownHookRegistrar shutdownHookRegistrar;

	public SystemBeanRegistrar(InternalSystemBeanRegistry internalBeanRegistry,
			SystemShutdownHookRegistrar shutdownHookRegistrar) {
		this.internalBeanRegistry = internalBeanRegistry;
		this.shutdownHookRegistrar = shutdownHookRegistrar;
	}

	public void registerBeans() {
		Set<Class<?>> beanClasses = PackageScanner.of(DifuseApplication.class).searchClassesAnnotatedWith(SystemBean.class);
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
