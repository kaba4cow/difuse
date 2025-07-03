package com.kaba4cow.difuse.core.dependency.provider;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Executable;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.Objects;

import com.kaba4cow.difuse.core.DifuseException;
import com.kaba4cow.difuse.core.annotation.dependency.NonRequired;
import com.kaba4cow.difuse.core.dependency.DependencyConsumer;

public class DependencyProviderSession {

	private final DependencyProvider dependencyProvider;

	private final DependencyConsumer dependencyConsumer;

	public DependencyProviderSession(DependencyProvider dependencyProvider, DependencyConsumer dependencyConsumer) {
		this.dependencyProvider = dependencyProvider;
		this.dependencyConsumer = dependencyConsumer;
	}

	public Object provideDependency(AnnotatedElement element, Type type) {
		Object dependency = dependencyProvider.provideDependency(element, type, dependencyConsumer);
		if (Objects.nonNull(dependency))
			return dependency;
		else if (element.isAnnotationPresent(NonRequired.class))
			return null;
		else
			throw new DifuseException(String.format("Could not provide dependency of type %s", type));
	}

	public Object[] provideDependencies(Executable executable) {
		Parameter[] parameters = executable.getParameters();
		Object[] values = new Object[parameters.length];
		for (int i = 0; i < values.length; i++)
			values[i] = provideDependency(parameters[i], parameters[i].getParameterizedType());
		return values;
	}

}
