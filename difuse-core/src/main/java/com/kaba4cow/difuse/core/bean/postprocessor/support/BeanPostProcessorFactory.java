package com.kaba4cow.difuse.core.bean.postprocessor.support;

import java.lang.reflect.Constructor;

import com.kaba4cow.difuse.core.DifuseException;
import com.kaba4cow.difuse.core.annotation.system.SystemComponent;
import com.kaba4cow.difuse.core.bean.postprocessor.BeanPostProcessor;
import com.kaba4cow.difuse.core.util.reflections.ConstructorScanner;

@SystemComponent
public class BeanPostProcessorFactory {

	public BeanPostProcessor createBeanPostProcessor(Class<? extends BeanPostProcessor> type) {
		try {
			Constructor<?> constructor = ConstructorScanner.of(type).findNoArgsConstructor();
			Object instance = constructor.newInstance();
			return type.cast(instance);
		} catch (Exception exception) {
			throw new DifuseException(String.format("Could not create BeanPostProcessor of type %s", type), exception);
		}
	}

}
