package com.kaba4cow.difuse.core.dependency.provider;

import java.lang.reflect.AnnotatedElement;

import com.kaba4cow.difuse.core.dependency.DependencyConsumer;
import com.kaba4cow.difuse.core.type.TypeDescriptor;

public interface DependencyProvider {

	Object provideDependency(AnnotatedElement element, TypeDescriptor type, DependencyConsumer dependencyConsumer);

}
