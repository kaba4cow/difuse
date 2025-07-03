package com.kaba4cow.difuse.core.bean.processor.impl;

import com.kaba4cow.difuse.core.annotation.lifecycle.PostConstruct;
import com.kaba4cow.difuse.core.bean.BeanLifecyclePhase;
import com.kaba4cow.difuse.core.bean.processor.BeanProcessor;
import com.kaba4cow.difuse.core.bean.provider.BeanProvider;
import com.kaba4cow.difuse.core.dependency.provider.DependencyProviderSession;

public class PostConstructBeanProcessor implements BeanProcessor {

	@Override
	public BeanLifecyclePhase getLifecyclePhase() {
		return BeanLifecyclePhase.POST_CONSTRUCTION;
	}

	@Override
	public Object process(Object bean, BeanProvider<?> beanProvider, DependencyProviderSession session) {
		invokePostConstructMethods(bean, beanProvider);
		return bean;
	}

	private void invokePostConstructMethods(Object bean, BeanProvider<?> beanProvider) {
		BeanProcessorReflections.invokeAnnotatedMethods(//
				bean, //
				beanProvider.getBeanSource(), //
				PostConstruct.class, //
				method -> new Object[0]//
		);
	}

}
