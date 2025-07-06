package com.kaba4cow.difuse.conditions.condition;

import com.kaba4cow.difuse.core.bean.source.BeanSource;

public interface BeanSourceCondition {

	boolean matches(BeanSource<?> beanSource);

}
