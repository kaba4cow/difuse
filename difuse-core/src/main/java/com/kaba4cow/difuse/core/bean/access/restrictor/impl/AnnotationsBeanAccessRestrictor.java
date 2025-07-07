package com.kaba4cow.difuse.core.bean.access.restrictor.impl;

import java.lang.annotation.Annotation;

import com.kaba4cow.difuse.core.annotation.access.restrictor.RestrictAnnotations;
import com.kaba4cow.difuse.core.bean.access.restrictor.BeanAccessRestrictor;
import com.kaba4cow.difuse.core.dependency.DependencyConsumer;

public class AnnotationsBeanAccessRestrictor implements BeanAccessRestrictor<RestrictAnnotations> {

	@Override
	public boolean isApplicable(RestrictAnnotations annotation) {
		return annotation.value().length > 0;
	}

	@Override
	public boolean restrictsAccess(RestrictAnnotations annotation, DependencyConsumer consumer) {
		Class<?> consumerClass = consumer.getConsumerClass();
		Class<? extends Annotation>[] allowedAnnotations = annotation.value();
		for (Class<? extends Annotation> allowedAnnotation : allowedAnnotations)
			if (consumerClass.isAnnotationPresent(allowedAnnotation))
				return true;
		return false;
	}

}
