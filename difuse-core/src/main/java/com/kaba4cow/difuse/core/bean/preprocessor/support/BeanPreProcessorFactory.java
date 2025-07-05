package com.kaba4cow.difuse.core.bean.preprocessor.support;

import java.lang.reflect.Constructor;

import com.kaba4cow.difuse.core.DifuseException;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.bean.preprocessor.BeanPreProcessor;
import com.kaba4cow.difuse.core.util.reflections.ConstructorScanner;

@SystemBean
public class BeanPreProcessorFactory {

	public BeanPreProcessor createBeanPreProcessor(Class<? extends BeanPreProcessor> type) {
		try {
			Constructor<?> constructor = ConstructorScanner.of(type).findNoArgsConstructor();
			Object instance = constructor.newInstance();
			return type.cast(instance);
		} catch (Exception exception) {
			throw new DifuseException(String.format("Could not create BeanPreProcessor of type %s", type), exception);
		}
	}

}
