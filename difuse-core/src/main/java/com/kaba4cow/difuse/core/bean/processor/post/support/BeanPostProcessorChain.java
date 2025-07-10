package com.kaba4cow.difuse.core.bean.processor.post.support;

import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.bean.processor.post.BeanPostProcessor;
import com.kaba4cow.difuse.core.bean.processor.post.BeanPostProcessorException;
import com.kaba4cow.difuse.core.bean.provider.impl.ClassBeanProvider;
import com.kaba4cow.difuse.core.dependency.provider.DependencyProviderSession;

@SystemBean
public class BeanPostProcessorChain {

	@Provided
	private BeanPostProcessorRegistry postProcessorRegistry;

	public Object postProcess(Object bean, ClassBeanProvider beanProvider, DependencyProviderSession session) {
		for (BeanPostProcessor beanPostProcessor : postProcessorRegistry.getActiveBeanPostProcessors())
			try {
				bean = beanPostProcessor.postProcess(bean, beanProvider, session);
			} catch (Exception exception) {
				throw new BeanPostProcessorException(String.format("BeanPostProcessor %s could not process bean of %s",
						beanPostProcessor.getClass().getName(), beanProvider), exception);
			}
		return bean;
	}

}
