package com.kaba4cow.difuse.core.bean.source.condition.impl;

import com.kaba4cow.difuse.core.annotation.conditional.Profile;
import com.kaba4cow.difuse.core.bean.source.BeanSource;
import com.kaba4cow.difuse.core.bean.source.condition.BeanSourceCondition;
import com.kaba4cow.difuse.core.bean.source.support.BeanSourceRegistry;
import com.kaba4cow.difuse.core.environment.Environment;

public class ProfileBeanSourceCondition implements BeanSourceCondition {

	@Override
	public boolean matches(BeanSource<?> beanSource, BeanSourceRegistry beanSourceRegistry, Environment environment) {
		String[] profiles = beanSource.getSourceElement().getAnnotation(Profile.class).value();
		for (String profile : profiles)
			if (environment.hasProfile(profile))
				return true;
		return false;
	}

}
