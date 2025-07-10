package com.kaba4cow.difuse.core.bean.access.provider;

import java.lang.annotation.Annotation;

import com.kaba4cow.difuse.core.bean.access.BeanAccessModifier;
import com.kaba4cow.difuse.core.dependency.DependencyConsumer;

public interface BeanAccessProvider<T extends Annotation> extends BeanAccessModifier<T> {

	boolean allowsAccess(T annotation, DependencyConsumer consumer);

}
