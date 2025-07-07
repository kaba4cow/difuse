package com.kaba4cow.difuse.core.bean.access.restrictor;

import java.lang.annotation.Annotation;

import com.kaba4cow.difuse.core.dependency.DependencyConsumer;

public interface BeanAccessRestrictor<T extends Annotation> {

	boolean isApplicable(T annotation);

	boolean restrictsAccess(T annotation, DependencyConsumer consumer);

}
