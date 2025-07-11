package com.kaba4cow.difuse.conditions.condition.support;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import java.util.Optional;

import com.kaba4cow.difuse.conditions.annotation.Conditional;
import com.kaba4cow.difuse.conditions.condition.BeanCondition;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.bean.source.BeanSource;

@SystemBean
public class BeanConditionMatcher {

	@Provided
	private BeanConditionRegistry conditionRegistry;

	public boolean matchesCondition(BeanSource<?> beanSource) {
		return findConditionClass(beanSource.getSourceElement())//
				.map(conditionRegistry::getCondition)//
				.map(condition -> condition.matches(beanSource))//
				.orElse(true);
	}

	private static Optional<Class<? extends BeanCondition>> findConditionClass(AnnotatedElement element) {
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
