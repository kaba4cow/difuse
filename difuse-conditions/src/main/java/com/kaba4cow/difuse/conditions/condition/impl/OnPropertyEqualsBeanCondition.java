package com.kaba4cow.difuse.conditions.condition.impl;

import com.kaba4cow.difuse.conditions.annotation.OnPropertyEquals;
import com.kaba4cow.difuse.conditions.condition.BeanCondition;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.bean.source.BeanSource;
import com.kaba4cow.difuse.core.environment.Environment;

public class OnPropertyEqualsBeanCondition implements BeanCondition {

	@Provided
	private Environment environment;

	@Override
	public boolean matches(BeanSource<?> beanSource) {
		OnPropertyEquals annotation = beanSource.getSourceElement().getAnnotation(OnPropertyEquals.class);
		String key = annotation.key();
		String value = annotation.value();
		return environment.hasProperty(key) && environment.getProperty(key).equals(value);
	}

}
