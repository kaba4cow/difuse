package com.kaba4cow.difuse.core.bean.processor.pre.support;

import com.kaba4cow.difuse.core.DifuseException;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.bean.processor.pre.BeanPreProcessor;
import com.kaba4cow.difuse.core.system.bean.SystemBeanInjector;

@SystemBean
public class BeanPreProcessorFactory {

	@Provided
	private SystemBeanInjector beanInjector;

	public BeanPreProcessor createBeanPreProcessor(Class<? extends BeanPreProcessor> type) {
		try {
			return beanInjector.createInstance(type);
		} catch (Exception exception) {
			throw new DifuseException(String.format("Could not create BeanPreProcessor of type %s", type), exception);
		}
	}

}
