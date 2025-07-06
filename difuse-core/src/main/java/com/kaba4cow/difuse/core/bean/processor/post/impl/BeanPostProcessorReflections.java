package com.kaba4cow.difuse.core.bean.processor.post.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.kaba4cow.difuse.core.bean.source.BeanSource;
import com.kaba4cow.difuse.core.util.reflections.FieldScanner;
import com.kaba4cow.difuse.core.util.reflections.MethodScanner;

public class BeanPostProcessorReflections {

	private BeanPostProcessorReflections() {}

	public static void invokeAnnotatedMethods(Object bean, BeanSource<?> beanSource, Class<? extends Annotation> annotation,
			Function<Method, Object[]> function) {
		for (Method method : findAnnotatedMethods(beanSource, annotation))
			try {
				method.setAccessible(true);
				method.invoke(bean, function.apply(method));
			} catch (Exception exception) {
				throw new RuntimeException(String.format("Could not invoke @%s method %s on bean of type %s",
						annotation.getSimpleName(), method.getName(), beanSource.getBeanClass().getClass()), exception);
			}
	}

	public static Set<Method> findAnnotatedMethods(BeanSource<?> beanSource, Class<? extends Annotation> annotation) {
		return MethodScanner.of(beanSource.getBeanClass()).findMethods().stream()//
				.filter(method -> !Modifier.isStatic(method.getModifiers()))//
				.filter(method -> method.isAnnotationPresent(annotation))//
				.collect(Collectors.toSet());
	}

	@SafeVarargs
	public static Set<Field> findFields(BeanSource<?> beanSource, Predicate<Field>... predicates) {
		return FieldScanner.of(beanSource.getBeanClass()).findFields().stream()//
				.filter(field -> !Modifier.isStatic(field.getModifiers()))//
				.filter(field -> !Modifier.isFinal(field.getModifiers()))//
				.filter(Arrays.stream(predicates).reduce(Predicate::and).orElse(field -> true))//
				.collect(Collectors.toSet());
	}

}
