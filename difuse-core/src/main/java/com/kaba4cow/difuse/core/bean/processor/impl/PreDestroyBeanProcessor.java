package com.kaba4cow.difuse.core.bean.processor.impl;

import com.kaba4cow.difuse.core.annotation.lifecycle.PreDestroy;
import com.kaba4cow.difuse.core.bean.BeanLifecyclePhase;
import com.kaba4cow.difuse.core.bean.processor.BeanProcessor;
import com.kaba4cow.difuse.core.bean.provider.BeanProvider;
import com.kaba4cow.difuse.core.dependency.provider.DependencyProviderSession;

public class PreDestroyBeanProcessor implements BeanProcessor {

	@Override
	public BeanLifecyclePhase getLifecyclePhase() {
		return BeanLifecyclePhase.POST_CONSTRUCTION;
	}

	@Override
	public Object process(Object bean, BeanProvider<?> beanProvider, DependencyProviderSession session) {
		beanProvider.addDestroyHook(bean, () -> invokePreDestroyMethods(bean, beanProvider));
		return bean;
	}

	private void invokePreDestroyMethods(Object bean, BeanProvider<?> beanProvider) {
		BeanProcessorReflections.invokeAnnotatedMethods(//
				bean, //
				beanProvider.getBeanSource(), //
				PreDestroy.class, //
				method -> new Object[0]//
		);
	}

}
