package com.kaba4cow.difuse.conditions.condition.impl;

import com.kaba4cow.difuse.conditions.annotation.Profile;
import com.kaba4cow.difuse.conditions.condition.BeanCondition;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.bean.source.BeanSource;
import com.kaba4cow.difuse.core.environment.Environment;

public class ProfileBeanCondition implements BeanCondition {

	@Provided
	private Environment environment;

	@Override
	public boolean matches(BeanSource<?> beanSource) {
		String[] profiles = beanSource.getSourceElement().getAnnotation(Profile.class).value();
		for (String profile : profiles)
			if (environment.hasProfile(profile))
				return true;
		return false;
	}

}
