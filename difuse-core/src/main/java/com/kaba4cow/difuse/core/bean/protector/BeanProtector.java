package com.kaba4cow.difuse.core.bean.protector;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import com.kaba4cow.difuse.core.annotation.access.ForAnnotations;
import com.kaba4cow.difuse.core.annotation.access.ForClasses;
import com.kaba4cow.difuse.core.annotation.access.ForContexts;
import com.kaba4cow.difuse.core.annotation.access.ForPackages;
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
		readAnnotation(element, ForClasses.class, ForClasses::value, allowedClasses);
		readAnnotation(element, ForContexts.class, ForContexts::value, allowedContexts);
		readAnnotation(element, ForPackages.class, ForPackages::value, allowedPackages);
		readAnnotation(element, ForAnnotations.class, ForAnnotations::value, allowedAnnotations);
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
