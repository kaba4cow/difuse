package com.kaba4cow.difuse.core.bean.processor.pre.support;

import java.util.Objects;
import java.util.Optional;

import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.bean.processor.pre.BeanPreProcessor;
import com.kaba4cow.difuse.core.bean.processor.pre.BeanPreProcessorException;
import com.kaba4cow.difuse.core.bean.source.BeanSource;

@SystemBean
public class GlobalBeanPreProcessor {

	@Provided
	private BeanPreProcessorRegistry beanPreProcessorRegistry;

	public <T extends BeanSource<?>> Optional<T> preProcess(T beanSource) {
		for (BeanPreProcessor beanPreProcessor : beanPreProcessorRegistry.getActiveBeanPreProcessors())
			try {
				beanSource = beanPreProcessor.preProcess(beanSource);
				if (Objects.isNull(beanSource))
					return Optional.empty();
			} catch (Exception exception) {
				throw new BeanPreProcessorException(String.format("BeanPreProcessor %s could not process %s",
						beanPreProcessor.getClass().getName(), beanSource), exception);
			}
		return Optional.of(beanSource);
	}

}
