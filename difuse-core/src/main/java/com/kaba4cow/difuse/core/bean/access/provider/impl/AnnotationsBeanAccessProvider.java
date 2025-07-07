package com.kaba4cow.difuse.core.bean.access.provider.impl;

import java.lang.annotation.Annotation;

import com.kaba4cow.difuse.core.annotation.access.AllowAnnotations;
import com.kaba4cow.difuse.core.bean.access.provider.BeanAccessProvider;
import com.kaba4cow.difuse.core.dependency.DependencyConsumer;

public class AnnotationsBeanAccessProvider implements BeanAccessProvider<AllowAnnotations> {

	@Override
	public boolean isApplicable(AllowAnnotations annotation) {
		return annotation.value().length > 0;
	}

	@Override
	public boolean allowsAccess(AllowAnnotations annotation, DependencyConsumer consumer) {
		Class<?> consumerClass = consumer.getConsumerClass();
		Class<? extends Annotation>[] allowedAnnotations = annotation.value();
		for (Class<? extends Annotation> allowedAnnotation : allowedAnnotations)
			if (consumerClass.isAnnotationPresent(allowedAnnotation))
				return true;
		return false;
	}

	@Override
	public Class<AllowAnnotations> getTargetAnnotation() {
		return AllowAnnotations.class;
	}

}
