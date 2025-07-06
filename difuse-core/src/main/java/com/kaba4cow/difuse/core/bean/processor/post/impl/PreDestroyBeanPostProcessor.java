package com.kaba4cow.difuse.core.bean.processor.post.impl;

import com.kaba4cow.difuse.core.annotation.lifecycle.PreDestroy;
import com.kaba4cow.difuse.core.bean.BeanLifecyclePhase;
import com.kaba4cow.difuse.core.bean.processor.post.BeanPostProcessor;
import com.kaba4cow.difuse.core.bean.provider.impl.ClassBeanProvider;
import com.kaba4cow.difuse.core.dependency.provider.DependencyProviderSession;

public class PreDestroyBeanPostProcessor implements BeanPostProcessor {

	@Override
	public BeanLifecyclePhase getLifecyclePhase() {
		return BeanLifecyclePhase.POST_CONSTRUCTION;
	}

	@Override
	public Object process(Object bean, ClassBeanProvider beanProvider, DependencyProviderSession session) {
		beanProvider.addDestroyHook(bean, () -> invokePreDestroyMethods(bean, beanProvider));
		return bean;
	}

	private void invokePreDestroyMethods(Object bean, ClassBeanProvider beanProvider) {
		BeanPostProcessorReflections.invokeAnnotatedMethods(//
				bean, //
				beanProvider.getBeanSource(), //
				PreDestroy.class, //
				method -> new Object[0]//
		);
	}

}
