package com.kaba4cow.difuse.core.bean.processor.support;

import java.lang.reflect.Constructor;

import com.kaba4cow.difuse.core.DifuseException;
import com.kaba4cow.difuse.core.annotation.application.CoreComponent;
import com.kaba4cow.difuse.core.bean.processor.BeanProcessor;
import com.kaba4cow.difuse.core.util.reflections.ConstructorScanner;

@CoreComponent
public class BeanProcessorFactory {

	public BeanProcessor createBeanProcessor(Class<? extends BeanProcessor> type) {
		try {
			Constructor<?> constructor = ConstructorScanner.of(type).findNoArgsConstructor();
			Object instance = constructor.newInstance();
			return type.cast(instance);
		} catch (Exception exception) {
			throw new DifuseException(String.format("Could not create BeanProcessor of type %s", type), exception);
		}
	}

}
