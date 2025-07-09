package com.kaba4cow.difuse.core.bean.processor.post.impl;

import com.kaba4cow.difuse.core.annotation.lifecycle.PostConstruct;
import com.kaba4cow.difuse.core.bean.processor.post.BeanLifecyclePhase;
import com.kaba4cow.difuse.core.bean.processor.post.BeanPostProcessor;
import com.kaba4cow.difuse.core.bean.provider.impl.ClassBeanProvider;
import com.kaba4cow.difuse.core.dependency.provider.DependencyProviderSession;

public class PostConstructBeanPostProcessor implements BeanPostProcessor {

	@Override
	public BeanLifecyclePhase getLifecyclePhase() {
		return BeanLifecyclePhase.POST_CONSTRUCTION;
	}

	@Override
	public Object postProcess(Object bean, ClassBeanProvider beanProvider, DependencyProviderSession session) {
		invokePostConstructMethods(bean, beanProvider);
		return bean;
	}

	private void invokePostConstructMethods(Object bean, ClassBeanProvider beanProvider) {
		BeanPostProcessorReflections.invokeAnnotatedMethods(//
				bean, //
				beanProvider.getBeanSource(), //
				PostConstruct.class, //
				method -> new Object[0]//
		);
	}

}
