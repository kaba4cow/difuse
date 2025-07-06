package com.kaba4cow.difuse.conditions.bean.preprocessor;

import com.kaba4cow.difuse.conditions.condition.support.BeanSourceConditionMatcher;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.bean.preprocessor.BeanPreProcessor;
import com.kaba4cow.difuse.core.bean.source.BeanSource;

public class ConditionBeanPreProcessor implements BeanPreProcessor {

	@Provided
	private BeanSourceConditionMatcher conditionMatcher;

	@Override
	public boolean process(BeanSource<?> beanSource) {
		return conditionMatcher.matchesCondition(beanSource);
	}

}
