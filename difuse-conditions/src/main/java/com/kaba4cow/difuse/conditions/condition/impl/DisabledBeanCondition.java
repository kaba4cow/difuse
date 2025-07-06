package com.kaba4cow.difuse.conditions.condition.impl;

import com.kaba4cow.difuse.conditions.condition.BeanCondition;
import com.kaba4cow.difuse.core.bean.source.BeanSource;

public class DisabledBeanCondition implements BeanCondition {

	@Override
	public boolean matches(BeanSource<?> beanSource) {
		return false;
	}

}
