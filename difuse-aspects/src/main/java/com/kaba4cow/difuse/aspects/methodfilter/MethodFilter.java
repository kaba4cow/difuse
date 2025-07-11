package com.kaba4cow.difuse.aspects.methodfilter;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import com.kaba4cow.difuse.aspects.annotation.filter.OnAnnotations;
import com.kaba4cow.difuse.aspects.annotation.filter.OnClasses;
import com.kaba4cow.difuse.aspects.annotation.filter.OnContexts;
import com.kaba4cow.difuse.aspects.annotation.filter.OnPackages;
import com.kaba4cow.difuse.core.bean.source.BeanSource;

public class MethodFilter {

	private final Set<Class<?>> allowedClasses = new HashSet<>();

	private final Set<Class<?>> allowedContexts = new HashSet<>();

	private final Set<String> allowedPackages = new HashSet<>();

	private final Set<Class<? extends Annotation>> allowedAnnotations = new HashSet<>();

	public MethodFilter(AnnotatedElement... elements) {
		for (AnnotatedElement element : elements)
			readAnnotations(element);
	}

	private void readAnnotations(AnnotatedElement element) {
		readAnnotation(element, OnClasses.class, OnClasses::value, allowedClasses);
		readAnnotation(element, OnContexts.class, OnContexts::value, allowedContexts);
		readAnnotation(element, OnPackages.class, OnPackages::value, allowedPackages);
		readAnnotation(element, OnAnnotations.class, OnAnnotations::value, allowedAnnotations);
	}

	private <T, S extends Annotation> void readAnnotation(AnnotatedElement element, Class<S> annotationClass,
			Function<S, T[]> function, Set<T> targetList) {
		if (element.isAnnotationPresent(annotationClass)) {
			S annotation = element.getAnnotation(annotationClass);
			Collections.addAll(targetList, function.apply(annotation));
		}
	}

	public boolean filter(Method method, BeanSource<?> beanSource) {
		return checkForClasses(beanSource)//
				|| checkForContexts(beanSource)//
				|| checkForPackages(beanSource)//
				|| checkForAnnotations(method);
	}

	private boolean checkForClasses(BeanSource<?> beanSource) {
		Class<?> declaringClass = beanSource.getDeclaringClass();
		for (Class<?> allowedClass : allowedClasses)
			if (allowedClass.isAssignableFrom(declaringClass))
				return true;
		return false;
	}

	private boolean checkForContexts(BeanSource<?> beanSource) {
		Class<?> consumerContext = beanSource.getContext().getSourceClass();
		for (Class<?> allowedContext : allowedContexts)
			if (consumerContext.equals(allowedContext))
				return true;
		return false;
	}

	private boolean checkForPackages(BeanSource<?> beanSource) {
		String consumerPackage = beanSource.getDeclaringClass().getPackage().getName();
		for (String allowedPackage : allowedPackages)
			if (consumerPackage.equals(allowedPackage))
				return true;
		return false;
	}

	private boolean checkForAnnotations(Method method) {
		for (Class<? extends Annotation> allowedAnnotation : allowedAnnotations)
			if (method.isAnnotationPresent(allowedAnnotation))
				return true;
		return false;
	}

}
