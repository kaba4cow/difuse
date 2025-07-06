package com.kaba4cow.difuse.core.bean.access.provider;

import java.lang.annotation.Annotation;

import com.kaba4cow.difuse.core.dependency.DependencyConsumer;

public interface BeanAccessProvider<T extends Annotation> {

	boolean allowsAccess(T annotation, DependencyConsumer consumer);

	Class<T> getTargetAnnotation();

}
