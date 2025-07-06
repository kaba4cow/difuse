package com.kaba4cow.difuse.core.bean.processor.post.impl;

import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.bean.BeanLifecyclePhase;
import com.kaba4cow.difuse.core.bean.processor.post.BeanPostProcessor;
import com.kaba4cow.difuse.core.bean.provider.BeanProvider;
import com.kaba4cow.difuse.core.bean.source.BeanSource;
import com.kaba4cow.difuse.core.dependency.provider.DependencyProviderSession;

public class MethodInjectionBeanPostProcessor implements BeanPostProcessor {

	@Override
	public BeanLifecyclePhase getLifecyclePhase() {
		return BeanLifecyclePhase.RUNTIME;
	}

	@Override
	public Object process(Object bean, BeanProvider<?> beanProvider, DependencyProviderSession session) {
		invokeProvidedMethods(bean, beanProvider.getBeanSource(), session);
		return bean;
	}

	private void invokeProvidedMethods(Object bean, BeanSource<?> beanSource, DependencyProviderSession session) {
		BeanPostProcessorReflections.invokeAnnotatedMethods(bean, beanSource, Provided.class, session::provideDependencies);
	}

}
