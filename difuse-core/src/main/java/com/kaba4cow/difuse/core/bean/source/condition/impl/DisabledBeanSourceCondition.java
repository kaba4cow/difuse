package com.kaba4cow.difuse.core.bean.source.condition.impl;

import com.kaba4cow.difuse.core.bean.source.BeanSource;
import com.kaba4cow.difuse.core.bean.source.condition.BeanSourceCondition;

public class DisabledBeanSourceCondition implements BeanSourceCondition {

	@Override
	public boolean matches(BeanSource<?> beanSource) {
		return false;
	}

}
