package com.kaba4cow.difuse.core.system.bean;

import java.lang.reflect.Constructor;
import java.util.Set;

import com.kaba4cow.difuse.core.DifuseApplication;
import com.kaba4cow.difuse.core.DifuseException;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.system.shutdownhook.SystemShutdownHookRegistrar;
import com.kaba4cow.difuse.core.util.reflections.PackageScanner;

public class SystemBeanRegistrar {

	private final SystemBeanRegistry beanRegistry;

	private final SystemShutdownHookRegistrar shutdownHookRegistrar;

	public SystemBeanRegistrar(SystemBeanRegistry beanRegistry, SystemShutdownHookRegistrar shutdownHookRegistrar) {
		this.beanRegistry = beanRegistry;
		this.shutdownHookRegistrar = shutdownHookRegistrar;
	}

	public void registerBeans() {
		registerBean(beanRegistry);
		Set<Class<?>> beanClasses = PackageScanner.of(DifuseApplication.class).searchClassesAnnotatedWith(SystemBean.class);
		for (Class<?> beanClass : beanClasses)
			registerBean(beanClass);
	}

	public <T> T registerBean(Class<T> type) {
		return registerBean(createInstance(type));
	}

	public <T> T registerBean(T bean) {
		beanRegistry.registerBean(bean);
		shutdownHookRegistrar.registerShutdownHooks(bean);
		return bean;
	}

	private <T> T createInstance(Class<T> type) {
		try {
			Constructor<?> constructor = type.getConstructor();
			T instance = type.cast(constructor.newInstance());
			return instance;
		} catch (Exception exception) {
			throw new DifuseException(String.format("Could not create SystemBean %s", type), exception);
		}
	}

}
