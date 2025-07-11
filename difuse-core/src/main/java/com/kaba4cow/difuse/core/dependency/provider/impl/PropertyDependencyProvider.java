package com.kaba4cow.difuse.core.dependency.provider.impl;

import java.lang.reflect.AnnotatedElement;

import com.kaba4cow.difuse.core.annotation.dependency.Property;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.dependency.DependencyConsumer;
import com.kaba4cow.difuse.core.dependency.provider.DependencyProvider;
import com.kaba4cow.difuse.core.environment.Environment;
import com.kaba4cow.difuse.core.type.TypeDescriptor;
import com.kaba4cow.difuse.core.type.converter.support.GlobalTypeConverter;

@SystemBean
public class PropertyDependencyProvider implements DependencyProvider {

	@Provided
	private Environment environment;

	@Provided
	private GlobalTypeConverter converter;

	@Override
	public Object provideDependency(AnnotatedElement element, TypeDescriptor type, DependencyConsumer dependencyConsumer) {
		String key = element.getAnnotation(Property.class).value();
		if (!environment.hasProperty(key))
			return null;
		Object value = environment.getProperty(key);
		return converter.convert(value, type);
	}

}
