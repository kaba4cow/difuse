package com.kaba4cow.difuse.core.bean.source.condition.support;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import java.util.Optional;

import com.kaba4cow.difuse.core.annotation.conditional.Conditional;
import com.kaba4cow.difuse.core.annotation.system.SystemComponent;
import com.kaba4cow.difuse.core.annotation.system.SystemDependency;
import com.kaba4cow.difuse.core.bean.source.BeanSource;
import com.kaba4cow.difuse.core.bean.source.condition.BeanSourceCondition;
import com.kaba4cow.difuse.core.bean.source.support.BeanSourceRegistry;
import com.kaba4cow.difuse.core.environment.Environment;

@SystemComponent
public class BeanSourceConditionMatcher {

	@SystemDependency
	private BeanSourceRegistry beanSourceRegistry;

	@SystemDependency
	private Environment environment;

	@SystemDependency
	private BeanSourceConditionFactory beanSourceConditionFactory;

	public boolean matchesCondition(BeanSource<?> beanSource) {
		return findConditionClass(beanSource.getSourceElement())//
				.map(beanSourceConditionFactory::createCondition)//
				.map(condition -> condition.matches(beanSource, beanSourceRegistry, environment))//
				.orElse(true);
	}

	private static Optional<Class<? extends BeanSourceCondition>> findConditionClass(AnnotatedElement element) {
		return findConditionalAnnotation(element)//
				.map(Conditional::value);
	}

	private static Optional<Conditional> findConditionalAnnotation(AnnotatedElement element) {
		return element.isAnnotationPresent(Conditional.class)//
				? Optional.of(element.getAnnotation(Conditional.class))//
				: Arrays.stream(element.getAnnotations())//
						.map(Annotation::annotationType)//
						.filter(type -> type.isAnnotationPresent(Conditional.class))//
						.map(type -> type.getAnnotation(Conditional.class))//
						.findFirst();
	}

}
