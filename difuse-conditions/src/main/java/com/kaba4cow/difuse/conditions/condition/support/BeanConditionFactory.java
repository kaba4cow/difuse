package com.kaba4cow.difuse.conditions.condition.support;

import java.lang.reflect.Constructor;

import com.kaba4cow.difuse.conditions.condition.BeanCondition;
import com.kaba4cow.difuse.core.DifuseException;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.system.bean.SystemBeanInjector;
import com.kaba4cow.difuse.core.util.reflections.ConstructorScanner;

@SystemBean
public class BeanConditionFactory {

	@Provided
	private SystemBeanInjector beanInjector;

	public BeanCondition createCondition(Class<? extends BeanCondition> type) {
		try {
			Constructor<?> constructor = ConstructorScanner.of(type).findNoArgsConstructor();
			Object instance = constructor.newInstance();
			beanInjector.injectDependencies(instance);
			return type.cast(instance);
		} catch (Exception exception) {
			throw new DifuseException(String.format("Could not create BeanCondition %s", type), exception);
		}
	}

}
