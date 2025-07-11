package com.kaba4cow.difuse.core.bean.processor.post.impl.injection.method;

import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.bean.processor.post.BeanLifecyclePhase;
import com.kaba4cow.difuse.core.bean.processor.post.BeanPostProcessor;
import com.kaba4cow.difuse.core.bean.processor.post.impl.BeanPostProcessorReflections;
import com.kaba4cow.difuse.core.bean.provider.impl.ClassBeanProvider;
import com.kaba4cow.difuse.core.bean.source.impl.ClassBeanSource;
import com.kaba4cow.difuse.core.dependency.provider.DependencyProviderSession;

public class MethodInjectionBeanPostProcessor implements BeanPostProcessor {

	@Override
	public BeanLifecyclePhase getLifecyclePhase() {
		return BeanLifecyclePhase.RUNTIME;
	}

	@Override
	public Object postProcess(Object bean, ClassBeanProvider beanProvider, DependencyProviderSession session) {
		invokeProvidedMethods(bean, beanProvider.getBeanSource(), session);
		return bean;
	}

	private void invokeProvidedMethods(Object bean, ClassBeanSource beanSource, DependencyProviderSession session) {
		BeanPostProcessorReflections.invokeAnnotatedMethods(bean, beanSource, Provided.class, session::provideDependencies);
	}

}
