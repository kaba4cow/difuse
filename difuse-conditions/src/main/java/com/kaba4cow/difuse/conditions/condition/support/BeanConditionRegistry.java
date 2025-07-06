package com.kaba4cow.difuse.conditions.condition.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.conditions.condition.BeanCondition;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;

@SystemBean
public class BeanConditionRegistry {

	private static final Logger log = LoggerFactory.getLogger("BeanConditionRegistry");

	private final Map<Class<? extends BeanCondition>, BeanCondition> registry = new ConcurrentHashMap<>();

	@Provided
	private BeanConditionFactory conditionFactory;

	public BeanCondition getCondition(Class<? extends BeanCondition> type) {
		return registry.computeIfAbsent(type, this::registerCondition);
	}

	private BeanCondition registerCondition(Class<? extends BeanCondition> type) {
		BeanCondition condition = conditionFactory.createCondition(type);
		log.debug("Registered BeanCondition {}", type.getName());
		return condition;
	}

}
