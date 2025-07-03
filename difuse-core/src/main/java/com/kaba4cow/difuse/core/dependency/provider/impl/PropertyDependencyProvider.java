package com.kaba4cow.difuse.core.dependency.provider.impl;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Type;

import com.kaba4cow.difuse.core.annotation.application.CoreComponent;
import com.kaba4cow.difuse.core.annotation.application.CoreDependency;
import com.kaba4cow.difuse.core.annotation.dependency.Property;
import com.kaba4cow.difuse.core.dependency.DependencyConsumer;
import com.kaba4cow.difuse.core.dependency.provider.DependencyProvider;
import com.kaba4cow.difuse.core.environment.Environment;

@CoreComponent
public class PropertyDependencyProvider implements DependencyProvider {

	@CoreDependency
	private Environment environment;

	@Override
	public Object provideDependency(AnnotatedElement element, Type type, DependencyConsumer dependencyConsumer) {
		String key = element.getAnnotation(Property.class).value();
		if (!environment.hasProperty(key))
			return null;
		return environment.getProperty(key);
	}

}
