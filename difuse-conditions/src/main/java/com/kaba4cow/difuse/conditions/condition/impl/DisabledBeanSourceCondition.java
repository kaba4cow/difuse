package com.kaba4cow.difuse.conditions.condition.impl;

import com.kaba4cow.difuse.conditions.condition.BeanSourceCondition;
import com.kaba4cow.difuse.core.bean.source.BeanSource;

public class DisabledBeanSourceCondition implements BeanSourceCondition {

	@Override
	public boolean matches(BeanSource<?> beanSource) {
		return false;
	}

}
