package com.kaba4cow.difuse.core.system.bean;

import java.util.Set;

import com.kaba4cow.difuse.core.system.bean.registry.impl.InternalSystemBeanRegistry;

public class SystemBeanInitializer {

	private final InternalSystemBeanRegistry beanRegistry;

	private final SystemBeanInjector beanInjector;

	public SystemBeanInitializer(InternalSystemBeanRegistry beanRegistry, SystemBeanInjector beanInjector) {
		this.beanRegistry = beanRegistry;
		this.beanInjector = beanInjector;
	}

	public void initializeBeans() {
		Set<Object> beans = beanRegistry.getAllBeans();
		for (Object bean : beans)
			beanInjector.injectDependencies(bean);
	}

}
