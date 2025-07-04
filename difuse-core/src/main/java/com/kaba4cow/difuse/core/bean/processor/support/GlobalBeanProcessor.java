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
		try {
			for (BeanProcessor beanProcessor : beanProcessorRegistry.getBeanProcessors())
				bean = beanProcessor.process(bean, beanProvider, session);
			return bean;
		} catch (Exception exception) {
			throw new BeanProcessorException(String.format("Could not process bean of %s", beanProvider));
		}
	}

}
