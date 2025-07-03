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

import com.kaba4cow.difuse.core.annotation.application.CoreComponent;
import com.kaba4cow.difuse.core.annotation.application.CoreDependency;
import com.kaba4cow.difuse.core.annotation.scope.Scope;
import com.kaba4cow.difuse.core.bean.source.BeanSource;
import com.kaba4cow.difuse.core.scope.handler.ScopeHandler;
import com.kaba4cow.difuse.core.scope.handler.impl.SingletonScopeHandler;

@CoreComponent
public class ScopeHandlerRegistry {

	private static final Logger log = LoggerFactory.getLogger("ScopeHandlerRegistry");

	@CoreDependency
	private ScopeHandlerFactory scopeHandlerFactory;

	private final Map<Class<? extends ScopeHandler>, ScopeHandler> registry = new ConcurrentHashMap<>();

	public Collection<ScopeHandler> getAllScopeHandlers() {
		return Collections.unmodifiableCollection(registry.values());
	}

	public ScopeHandler getScopeHandler(BeanSource<?> beanSource) {
		return findScopeAnnotation(beanSource.getSourceElement())//
				.map(Scope::value)//
				.map(this::getScopeHandler)//
				.orElseGet(this::getDefaultScopeHandler);
	}

	private static Optional<Scope> findScopeAnnotation(AnnotatedElement element) {
		return element.isAnnotationPresent(Scope.class)//
				? Optional.of(element.getAnnotation(Scope.class))//
				: Arrays.stream(element.getDeclaredAnnotations())//
						.map(Annotation::annotationType)//
						.filter(annotation -> annotation.isAnnotationPresent(Scope.class))//
						.map(annotation -> annotation.getAnnotation(Scope.class))//
						.findFirst();
	}

	private ScopeHandler getScopeHandler(Class<? extends ScopeHandler> type) {
		return registry.computeIfAbsent(type, key -> {
			ScopeHandler scopeHandler = scopeHandlerFactory.createScopeHandler(type);
			log.debug("Registered ScopeHandler {}", type);
			return scopeHandler;
		});
	}

	private ScopeHandler getDefaultScopeHandler() {
		return getScopeHandler(SingletonScopeHandler.class);
	}

}
