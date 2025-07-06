package com.kaba4cow.difuse.conditions.condition.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.kaba4cow.difuse.conditions.condition.BeanSourceCondition;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;

@SystemBean
public class BeanSourceConditionRegistry {

	private final Map<Class<? extends BeanSourceCondition>, BeanSourceCondition> registry = new ConcurrentHashMap<>();

	@Provided
	private BeanSourceConditionFactory conditionFactory;

	public BeanSourceCondition getCondition(Class<? extends BeanSourceCondition> type) {
		return registry.computeIfAbsent(type, conditionFactory::createCondition);
	}

}
