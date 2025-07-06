package com.kaba4cow.difuse.core.bean.processor.post.support;

import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.bean.processor.post.BeanPostProcessor;
import com.kaba4cow.difuse.core.bean.processor.post.BeanPostProcessorException;
import com.kaba4cow.difuse.core.bean.provider.BeanProvider;
import com.kaba4cow.difuse.core.dependency.provider.DependencyProviderSession;

@SystemBean
public class GlobalBeanPostProcessor {

	@Provided
	private BeanPostProcessorRegistry beanPostProcessorRegistry;

	public Object process(Object bean, BeanProvider<?> beanProvider, DependencyProviderSession session) {
		for (BeanPostProcessor beanPostProcessor : beanPostProcessorRegistry.getActiveBeanPostProcessors())
			try {
				bean = beanPostProcessor.process(bean, beanProvider, session);
			} catch (Exception exception) {
				throw new BeanPostProcessorException(String.format("BeanPostProcessor %s could not process bean of %s",
						beanPostProcessor.getClass().getName(), beanProvider), exception);
			}
		return bean;
	}

}
