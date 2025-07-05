package com.kaba4cow.difuse.core.scope.support;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.scope.Scoped;
import com.kaba4cow.difuse.core.annotation.system.SystemComponent;
import com.kaba4cow.difuse.core.annotation.system.SystemDependency;
import com.kaba4cow.difuse.core.bean.source.BeanSource;
import com.kaba4cow.difuse.core.scope.Scope;
import com.kaba4cow.difuse.core.scope.impl.SingletonScope;

@SystemComponent
public class ScopeRegistry {

	private static final Logger log = LoggerFactory.getLogger("ScopeRegistry");

	@SystemDependency
	private ScopeFactory scopeHandlerFactory;

	private final Map<Class<? extends Scope>, Scope> registry = new ConcurrentHashMap<>();

	public Collection<Scope> getAllScopes() {
		return Collections.unmodifiableCollection(registry.values());
	}

	public Scope getScope(BeanSource<?> beanSource) {
		return findScopeAnnotation(beanSource.getSourceElement())//
				.map(Scoped::value)//
				.map(this::getScope)//
				.orElseGet(this::getDefaultScopeHandler);
	}

	private static Optional<Scoped> findScopeAnnotation(AnnotatedElement element) {
		return element.isAnnotationPresent(Scoped.class)//
				? Optional.of(element.getAnnotation(Scoped.class))//
				: Arrays.stream(element.getDeclaredAnnotations())//
						.map(Annotation::annotationType)//
						.filter(annotation -> annotation.isAnnotationPresent(Scoped.class))//
						.map(annotation -> annotation.getAnnotation(Scoped.class))//
						.findFirst();
	}

	private Scope getScope(Class<? extends Scope> type) {
		return registry.computeIfAbsent(type, key -> {
			Scope scopeHandler = scopeHandlerFactory.createScope(type);
			log.debug("Registered Scope {}", type);
			return scopeHandler;
		});
	}

	private Scope getDefaultScopeHandler() {
		return getScope(SingletonScope.class);
	}

}
