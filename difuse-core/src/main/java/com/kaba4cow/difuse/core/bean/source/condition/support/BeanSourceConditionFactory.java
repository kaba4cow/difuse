package com.kaba4cow.difuse.core.bean.source.condition.support;

import java.lang.reflect.Constructor;

import com.kaba4cow.difuse.core.DifuseException;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.bean.source.condition.BeanSourceCondition;
import com.kaba4cow.difuse.core.system.bean.SystemBeanInjector;
import com.kaba4cow.difuse.core.util.reflections.ConstructorScanner;

@SystemBean
public class BeanSourceConditionFactory {

	@Provided
	private SystemBeanInjector beanInjector;

	public BeanSourceCondition createCondition(Class<? extends BeanSourceCondition> type) {
		try {
			Constructor<?> constructor = ConstructorScanner.of(type).findNoArgsConstructor();
			Object instance = constructor.newInstance();
			beanInjector.injectDependencies(instance);
			return type.cast(instance);
		} catch (Exception exception) {
			throw new DifuseException(String.format("Could not create BeanSourceCondition %s", type), exception);
		}
	}

}
