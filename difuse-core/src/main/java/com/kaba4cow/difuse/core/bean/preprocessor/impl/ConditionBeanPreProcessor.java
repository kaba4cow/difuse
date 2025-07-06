package com.kaba4cow.difuse.core.bean.preprocessor.impl;

import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.bean.preprocessor.BeanPreProcessor;
import com.kaba4cow.difuse.core.bean.source.BeanSource;
import com.kaba4cow.difuse.core.bean.source.condition.support.BeanSourceConditionMatcher;

public class ConditionBeanPreProcessor implements BeanPreProcessor {

	@Provided
	private BeanSourceConditionMatcher conditionMatcher;

	@Override
	public boolean process(BeanSource<?> beanSource) {
		return conditionMatcher.matchesCondition(beanSource);
	}

}
