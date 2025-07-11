package com.kaba4cow.difuse.conditions.condition.impl;

import com.kaba4cow.difuse.conditions.annotation.OnPropertyExists;
import com.kaba4cow.difuse.conditions.condition.BeanCondition;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.bean.source.BeanSource;
import com.kaba4cow.difuse.core.environment.Environment;

public class OnPropertyExistsBeanCondition implements BeanCondition {

	@Provided
	private Environment environment;

	@Override
	public boolean matches(BeanSource<?> beanSource) {
		String key = beanSource.getSourceElement().getAnnotation(OnPropertyExists.class).value();
		return environment.hasProperty(key);
	}

}
