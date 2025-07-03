package com.kaba4cow.difuse.core.bean.source.condition;

import com.kaba4cow.difuse.core.bean.source.BeanSource;
import com.kaba4cow.difuse.core.bean.source.support.BeanSourceRegistry;
import com.kaba4cow.difuse.core.environment.Environment;

public interface BeanSourceCondition {

	boolean matches(BeanSource<?> beanSource, BeanSourceRegistry beanSourceRegistry, Environment environment);

}
