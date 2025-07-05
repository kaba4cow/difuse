package com.kaba4cow.difuse.core.bean.source.support;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.system.SystemComponent;
import com.kaba4cow.difuse.core.annotation.system.SystemDependency;
import com.kaba4cow.difuse.core.bean.source.BeanSource;
import com.kaba4cow.difuse.core.bean.source.condition.support.BeanSourceConditionMatcher;
import com.kaba4cow.difuse.core.bean.source.validator.support.GlobalBeanSourceValidator;

@SystemComponent
public class BeanSourceRegistry {

	private static final Logger log = LoggerFactory.getLogger("BeanSourceRegistry");

	private final Map<Class<?>, Set<BeanSource<?>>> registry = new ConcurrentHashMap<>();

	@SystemDependency
	private BeanSourceConditionMatcher beanSourceConditionMatcher;

	@SystemDependency
	private GlobalBeanSourceValidator globalBeanSourceValidator;

	public void register(BeanSource<?> beanSource) {
		globalBeanSourceValidator.validate(beanSource);
		if (beanSourceConditionMatcher.matchesCondition(beanSource)) {
			registry.computeIfAbsent(beanSource.getClass(), key -> ConcurrentHashMap.newKeySet()).add(beanSource);
			log.debug("Registered {}", beanSource);
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends BeanSource<?>> Set<T> getBeanSources(Class<T> type) {
		return registry.containsKey(type)//
				? (Set<T>) Collections.unmodifiableSet(registry.get(type))//
				: Collections.emptySet();
	}

}
