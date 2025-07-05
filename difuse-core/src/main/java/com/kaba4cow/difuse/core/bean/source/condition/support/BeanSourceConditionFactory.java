package com.kaba4cow.difuse.core.bean.source.condition.support;

import java.lang.reflect.Constructor;

import com.kaba4cow.difuse.core.DifuseException;
import com.kaba4cow.difuse.core.annotation.system.SystemComponent;
import com.kaba4cow.difuse.core.bean.source.condition.BeanSourceCondition;
import com.kaba4cow.difuse.core.util.reflections.ConstructorScanner;

@SystemComponent
public class BeanSourceConditionFactory {

	public BeanSourceCondition createCondition(Class<? extends BeanSourceCondition> type) {
		try {
			Constructor<?> constructor = ConstructorScanner.of(type).findNoArgsConstructor();
			Object instance = constructor.newInstance();
			return type.cast(instance);
		} catch (Exception exception) {
			throw new DifuseException(String.format("Could not create BeanSourceCondition %s", type), exception);
		}
	}

}
