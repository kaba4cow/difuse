package com.kaba4cow.difuse.core.dependency.provider.impl;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Type;

import com.kaba4cow.difuse.core.annotation.dependency.Property;
import com.kaba4cow.difuse.core.annotation.system.SystemComponent;
import com.kaba4cow.difuse.core.annotation.system.SystemDependency;
import com.kaba4cow.difuse.core.dependency.DependencyConsumer;
import com.kaba4cow.difuse.core.dependency.provider.DependencyProvider;

@SystemComponent
public class DelegatingDependencyProvider implements DependencyProvider {

	@SystemDependency
	private PropertyDependencyProvider propertyDependencyProvider;

	@SystemDependency
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
