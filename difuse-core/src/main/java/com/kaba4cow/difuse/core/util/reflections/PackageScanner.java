package com.kaba4cow.difuse.core.util.reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

public class PackageScanner {

	private final Reflections reflections;

	private PackageScanner(Reflections reflections) {
		this.reflections = reflections;
	}

	public static PackageScanner of(Class<?> type) {
		String sourcePackage = type.getPackage().getName();
		FilterBuilder filter = new FilterBuilder()//
				.includePackage(sourcePackage);
		Reflections reflections = new Reflections(new ConfigurationBuilder()//
				.setUrls(ClasspathHelper.forPackage(sourcePackage))//
				.addScanners(Scanners.TypesAnnotated, Scanners.SubTypes)//
				.filterInputsBy(filter));
		return new PackageScanner(reflections);
	}

	public <T> Set<Class<? extends T>> searchSubTypesOf(Class<T> type) {
		return reflections.getSubTypesOf(type).stream()//
				.filter(t -> !Modifier.isAbstract(t.getModifiers()))//
				.collect(Collectors.toSet());
	}

	public Set<Class<?>> searchClassesAnnotatedWith(Class<? extends Annotation> annotation) {
		Set<Class<?>> annotations = searchAnnotations(annotation);
		annotations.add(annotation);
		Set<Class<?>> classes = searchAnnotatedClasses(annotations);
		return classes;
	}

	private Set<Class<?>> searchAnnotations(Class<? extends Annotation> annotation) {
		return reflections.getTypesAnnotatedWith(annotation).stream()//
				.filter(Class::isAnnotation)//
				.collect(Collectors.toSet());
	}

	private Set<Class<?>> searchAnnotatedClasses(Set<Class<?>> annotations) {
		return annotations.stream()//
				.flatMap(this::streamAnnotatedClasses)//
				.collect(Collectors.toSet());
	}

	@SuppressWarnings("unchecked")
	private Stream<Class<?>> streamAnnotatedClasses(Class<?> annotation) {
		return reflections.getTypesAnnotatedWith((Class<? extends Annotation>) annotation).stream()//
				.filter(type -> !type.isAnnotation());
	}

}
