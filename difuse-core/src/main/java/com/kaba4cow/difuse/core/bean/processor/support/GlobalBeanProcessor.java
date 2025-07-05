package com.kaba4cow.difuse.core.bean.processor.support;

import com.kaba4cow.difuse.core.annotation.system.SystemComponent;
import com.kaba4cow.difuse.core.annotation.system.SystemDependency;
import com.kaba4cow.difuse.core.bean.processor.BeanProcessor;
import com.kaba4cow.difuse.core.bean.processor.BeanProcessorException;
import com.kaba4cow.difuse.core.bean.provider.BeanProvider;
import com.kaba4cow.difuse.core.dependency.provider.DependencyProviderSession;

@SystemComponent
public class GlobalBeanProcessor {

	@SystemDependency
	private BeanProcessorRegistry beanProcessorRegistry;

	public Object process(Object bean, BeanProvider<?> beanProvider, DependencyProviderSession session) {
		for (BeanProcessor beanProcessor : beanProcessorRegistry.getBeanProcessors())
			try {
				bean = beanProcessor.process(bean, beanProvider, session);
			} catch (Exception exception) {
				throw new BeanProcessorException(String.format("BeanProcessor %s could not process bean of %s",
						beanProcessor.getClass().getName(), beanProvider), exception);
			}
		return bean;
	}

}
