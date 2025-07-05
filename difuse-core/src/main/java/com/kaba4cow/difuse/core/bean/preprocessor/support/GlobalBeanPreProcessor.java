package com.kaba4cow.difuse.core.bean.preprocessor.support;

import com.kaba4cow.difuse.core.annotation.system.SystemComponent;
import com.kaba4cow.difuse.core.annotation.system.SystemDependency;
import com.kaba4cow.difuse.core.bean.preprocessor.BeanPreProcessor;
import com.kaba4cow.difuse.core.bean.preprocessor.BeanPreProcessorException;
import com.kaba4cow.difuse.core.bean.source.BeanSource;

@SystemComponent
public class GlobalBeanPreProcessor {

	@SystemDependency
	private BeanPreProcessorRegistry beanPreProcessorRegistry;

	public boolean process(BeanSource<?> beanSource) {
		for (BeanPreProcessor beanPreProcessor : beanPreProcessorRegistry.getBeanPreProcessors())
			try {
				if (!beanPreProcessor.process(beanSource))
					return false;
			} catch (Exception exception) {
				throw new BeanPreProcessorException(String.format("BeanPreProcessor %s could not process %s",
						beanPreProcessor.getClass().getName(), beanSource), exception);
			}
		return true;
	}

}
