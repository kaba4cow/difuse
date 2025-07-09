package com.kaba4cow.difuse.conditions.bean.processor.pre;

import com.kaba4cow.difuse.conditions.condition.support.BeanConditionMatcher;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.bean.processor.pre.BeanPreProcessor;
import com.kaba4cow.difuse.core.bean.source.BeanSource;

public class ConditionBeanPreProcessor implements BeanPreProcessor {

	@Provided
	private BeanConditionMatcher conditionMatcher;

	@Override
	public <T extends BeanSource<?>> T process(T beanSource) {
		return conditionMatcher.matchesCondition(beanSource)//
				? beanSource//
				: null;
	}

}
