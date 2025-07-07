package com.kaba4cow.difuse.core.bean.processor.post.support;

import com.kaba4cow.difuse.core.DifuseException;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.bean.processor.post.BeanPostProcessor;
import com.kaba4cow.difuse.core.system.bean.SystemBeanInjector;

@SystemBean
public class BeanPostProcessorFactory {

	@Provided
	private SystemBeanInjector beanInjector;

	public BeanPostProcessor createBeanPostProcessor(Class<? extends BeanPostProcessor> type) {
		try {
			return beanInjector.createInstance(type);
		} catch (Exception exception) {
			throw new DifuseException(String.format("Could not create BeanPostProcessor of type %s", type), exception);
		}
	}

}
