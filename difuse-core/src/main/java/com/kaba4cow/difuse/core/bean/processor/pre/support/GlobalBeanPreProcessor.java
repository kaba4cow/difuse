package com.kaba4cow.difuse.core.bean.processor.pre.support;

import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.bean.processor.pre.BeanPreProcessor;
import com.kaba4cow.difuse.core.bean.processor.pre.BeanPreProcessorException;
import com.kaba4cow.difuse.core.bean.source.BeanSource;

@SystemBean
public class GlobalBeanPreProcessor {

	@Provided
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
