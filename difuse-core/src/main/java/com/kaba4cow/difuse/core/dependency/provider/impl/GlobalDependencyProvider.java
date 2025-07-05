package com.kaba4cow.difuse.core.dependency.provider.impl;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Type;

import com.kaba4cow.difuse.core.annotation.dependency.Property;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.dependency.DependencyConsumer;
import com.kaba4cow.difuse.core.dependency.provider.DependencyProvider;

@SystemBean
public class GlobalDependencyProvider implements DependencyProvider {

	@Provided
	private PropertyDependencyProvider propertyDependencyProvider;

	@Provided
	private BeanDependencyProvider beanDependencyProvider;

	@Override
	public Object provideDependency(AnnotatedElement element, Type type, DependencyConsumer dependencyConsumer) {
		return getDependencyProvider(element).provideDependency(element, type, dependencyConsumer);
	}

	private DependencyProvider getDependencyProvider(AnnotatedElement element) {
		return element.isAnnotationPresent(Property.class)//
				? propertyDependencyProvider//
				: beanDependencyProvider;
	}

}
