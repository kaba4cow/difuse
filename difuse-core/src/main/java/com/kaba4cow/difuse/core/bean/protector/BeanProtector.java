package com.kaba4cow.difuse.core.bean.protector;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import com.kaba4cow.difuse.core.annotation.access.AllowAnnotations;
import com.kaba4cow.difuse.core.annotation.access.AllowClasses;
import com.kaba4cow.difuse.core.annotation.access.AllowContexts;
import com.kaba4cow.difuse.core.annotation.access.AllowPackages;
import com.kaba4cow.difuse.core.dependency.DependencyConsumer;

public class BeanProtector {

	private final Set<Class<?>> allowedClasses = new HashSet<>();

	private final Set<Class<?>> allowedContexts = new HashSet<>();

	private final Set<String> allowedPackages = new HashSet<>();

	private final Set<Class<? extends Annotation>> allowedAnnotations = new HashSet<>();

	private boolean restricting = false;

	BeanProtector(AnnotatedElement... elements) {
		for (AnnotatedElement element : elements)
			readAnnotations(element);
	}

	private void readAnnotations(AnnotatedElement element) {
		readAnnotation(element, AllowClasses.class, AllowClasses::value, allowedClasses);
		readAnnotation(element, AllowContexts.class, AllowContexts::value, allowedContexts);
		readAnnotation(element, AllowPackages.class, AllowPackages::value, allowedPackages);
		readAnnotation(element, AllowAnnotations.class, AllowAnnotations::value, allowedAnnotations);
	}

	private <T, S extends Annotation> void readAnnotation(AnnotatedElement element, Class<S> annotationClass,
			Function<S, T[]> function, Set<T> targetList) {
		if (element.isAnnotationPresent(annotationClass)) {
			restricting = true;
			S annotation = element.getAnnotation(annotationClass);
			Collections.addAll(targetList, function.apply(annotation));
		}
	}

	public boolean canBeAccessedBy(DependencyConsumer consumer) {
		return !restricting//
				|| checkForClasses(consumer)//
				|| checkForContexts(consumer)//
				|| checkForPackages(consumer)//
				|| checkForAnnotations(consumer);
	}

	private boolean checkForClasses(DependencyConsumer consumer) {
		Class<?> consumerClass = consumer.getConsumerClass();
		for (Class<?> allowedClass : allowedClasses)
			if (allowedClass.isAssignableFrom(consumerClass))
				return true;
		return false;
	}

	private boolean checkForContexts(DependencyConsumer consumer) {
		Class<?> consumerContext = consumer.getContextSource().getSourceClass();
		for (Class<?> allowedContext : allowedContexts)
			if (consumerContext.equals(allowedContext))
				return true;
		return false;
	}

	private boolean checkForPackages(DependencyConsumer consumer) {
		String consumerPackage = consumer.getConsumerClass().getPackage().getName();
		for (String allowedPackage : allowedPackages)
			if (consumerPackage.equals(allowedPackage))
				return true;
		return false;
	}

	private boolean checkForAnnotations(DependencyConsumer consumer) {
		Class<?> consumerClass = consumer.getConsumerClass();
		for (Class<? extends Annotation> allowedAnnotation : allowedAnnotations)
			if (consumerClass.isAnnotationPresent(allowedAnnotation))
				return true;
		return false;
	}

	@Override
	public String toString() {
		return String.format(
				"BeanProtector [restricting=%s, allowedClasses=%s, allowedContexts=%s, allowedPackages=%s, allowedAnnotations=%s]",
				restricting, allowedClasses, allowedContexts, allowedPackages, allowedAnnotations);
	}

}
