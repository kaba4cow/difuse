package com.kaba4cow.difuse.core.dependency.provider;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Type;

import com.kaba4cow.difuse.core.dependency.DependencyConsumer;

public interface DependencyProvider {

	Object provideDependency(AnnotatedElement element, Type type, DependencyConsumer dependencyConsumer);

}
