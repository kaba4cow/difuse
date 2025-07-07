package com.kaba4cow.difuse.core.dependency.provider.impl;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Type;

import com.kaba4cow.difuse.core.annotation.dependency.Property;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.dependency.DependencyConsumer;
import com.kaba4cow.difuse.core.dependency.provider.DependencyProvider;
import com.kaba4cow.difuse.core.environment.Environment;
import com.kaba4cow.difuse.core.property.converter.support.GlobalPropertyConverter;

@SystemBean
public class PropertyDependencyProvider implements DependencyProvider {

	@Provided
	private Environment environment;

	@Provided
	private GlobalPropertyConverter converter;

	@Override
	public Object provideDependency(AnnotatedElement element, Type type, DependencyConsumer dependencyConsumer) {
		String key = element.getAnnotation(Property.class).value();
		if (!environment.hasProperty(key))
			return null;
		return environment.getProperty(key);
	}

}
