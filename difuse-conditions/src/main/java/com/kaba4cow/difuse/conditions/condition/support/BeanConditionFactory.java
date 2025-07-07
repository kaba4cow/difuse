package com.kaba4cow.difuse.conditions.condition.support;

import com.kaba4cow.difuse.conditions.condition.BeanCondition;
import com.kaba4cow.difuse.core.DifuseException;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.system.bean.SystemBeanInjector;

@SystemBean
public class BeanConditionFactory {

	@Provided
	private SystemBeanInjector beanInjector;

	public BeanCondition createCondition(Class<? extends BeanCondition> type) {
		try {
			return beanInjector.createInstance(type);
		} catch (Exception exception) {
			throw new DifuseException(String.format("Could not create BeanCondition %s", type), exception);
		}
	}

}
