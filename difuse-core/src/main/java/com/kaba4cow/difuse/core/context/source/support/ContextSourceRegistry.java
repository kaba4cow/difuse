package com.kaba4cow.difuse.core.context.source.support;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.application.CoreComponent;
import com.kaba4cow.difuse.core.annotation.application.CoreDependency;
import com.kaba4cow.difuse.core.bean.source.condition.support.BeanSourceConditionMatcher;
import com.kaba4cow.difuse.core.context.source.ContextSource;
import com.kaba4cow.difuse.core.context.source.configuration.ContextSourceConfiguration;

@CoreComponent
public class ContextSourceRegistry {

	private static final Logger log = LoggerFactory.getLogger("ContextSourceRegistry");

	private final Map<Class<?>, ContextSource> registry = new ConcurrentHashMap<>();

	@CoreDependency
	private BeanSourceConditionMatcher beanSourceConditionMatcher;

	public ContextSourceRegistry() {}

	public void register(Class<?> sourceClass, ContextSource contextSource) {
		if (!registry.containsKey(sourceClass)) {
			registry.put(sourceClass, contextSource);
			log.debug("Registered {}", contextSource);
		}
	}

	public boolean contains(Class<?> sourceClass) {
		return registry.containsKey(sourceClass);
	}

	public Collection<ContextSource> getContextSources() {
		return Collections.unmodifiableCollection(registry.values());
	}

	public <T> Set<T> collectConfigurations(Function<ContextSourceConfiguration, Set<T>> supplier) {
		return registry.values().stream()//
				.map(ContextSource::getConfiguration)//
				.map(supplier)//
				.flatMap(Set::stream)//
				.collect(Collectors.toSet());
	}

}
