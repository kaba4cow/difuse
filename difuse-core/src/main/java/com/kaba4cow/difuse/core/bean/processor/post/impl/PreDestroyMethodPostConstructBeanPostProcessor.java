package com.kaba4cow.difuse.core.bean.processor.post.impl;

import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.lifecycle.PreDestroy;
import com.kaba4cow.difuse.core.bean.destroyhook.BeanDestroyHookRegistryPool;
import com.kaba4cow.difuse.core.bean.processor.post.phase.PostConstructBeanPostProcessor;
import com.kaba4cow.difuse.core.bean.provider.impl.ClassBeanProvider;
import com.kaba4cow.difuse.core.dependency.provider.DependencyProviderSession;

public class PreDestroyMethodPostConstructBeanPostProcessor extends PostConstructBeanPostProcessor {

	@Provided
	private BeanDestroyHookRegistryPool destroyHookRegistryPool;

	@Override
	public Object postProcess(Object bean, ClassBeanProvider beanProvider, DependencyProviderSession session) {
		destroyHookRegistryPool.of(beanProvider)//
				.addDestroyHook(bean, () -> invokePreDestroyMethods(bean, beanProvider));
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
