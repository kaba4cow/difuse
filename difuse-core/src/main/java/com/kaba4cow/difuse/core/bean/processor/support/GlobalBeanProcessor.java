package com.kaba4cow.difuse.core.bean.processor.support;

import com.kaba4cow.difuse.core.annotation.application.CoreComponent;
import com.kaba4cow.difuse.core.annotation.application.CoreDependency;
import com.kaba4cow.difuse.core.bean.processor.BeanProcessor;
import com.kaba4cow.difuse.core.bean.processor.BeanProcessorException;
import com.kaba4cow.difuse.core.bean.provider.BeanProvider;
import com.kaba4cow.difuse.core.dependency.provider.DependencyProviderSession;

@CoreComponent
public class GlobalBeanProcessor {

	@CoreDependency
	private BeanProcessorRegistry beanProcessorRegistry;

	public Object process(Object bean, BeanProvider<?> beanProvider, DependencyProviderSession session) {
		for (BeanProcessor beanProcessor : beanProcessorRegistry.getBeanProcessors())
			try {
				bean = beanProcessor.process(bean, beanProvider, session);
			} catch (Exception exception) {
				throw new BeanProcessorException(String.format("BeanProcessor %s could not process bean of %s",
						beanProcessor.getClass().getName(), beanProvider));
			}
		return bean;
	}

}
