package com.kaba4cow.difuse.core.context.support;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.DifuseApplication;
import com.kaba4cow.difuse.core.context.Context;
import com.kaba4cow.difuse.core.context.configuration.ContextConfiguration;

public class ContextRegistry {

	private static final Logger log = LoggerFactory.getLogger("ContextRegistry");

	private final Map<Class<?>, Context> registry = new ConcurrentHashMap<>();

	public void register(Class<?> sourceClass, Context context) {
		if (!registry.containsKey(sourceClass)) {
			registry.put(sourceClass, context);
			log.debug("Registered {}", context);
		}
	}

	public boolean contains(Class<?> sourceClass) {
		return registry.containsKey(sourceClass);
	}

	public Collection<Context> getContexts() {
		return Collections.unmodifiableCollection(registry.values());
	}

	public Set<Class<?>> getSourceClasses() {
		Set<Class<?>> sourceClasses = new HashSet<>(registry.keySet());
		sourceClasses.add(DifuseApplication.class);
		return Collections.unmodifiableSet(sourceClasses);
	}

	public <T> Set<T> collectConfigurations(Function<ContextConfiguration, Set<T>> supplier) {
		return registry.values().stream()//
				.map(Context::getConfiguration)//
				.map(supplier)//
				.flatMap(Set::stream)//
				.collect(Collectors.toSet());
	}

}
