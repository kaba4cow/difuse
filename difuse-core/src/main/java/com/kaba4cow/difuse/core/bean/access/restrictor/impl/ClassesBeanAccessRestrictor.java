package com.kaba4cow.difuse.core.bean.access.restrictor.impl;

import com.kaba4cow.difuse.core.annotation.access.restrictor.RestrictClasses;
import com.kaba4cow.difuse.core.bean.access.restrictor.BeanAccessRestrictor;
import com.kaba4cow.difuse.core.dependency.DependencyConsumer;

public class ClassesBeanAccessRestrictor implements BeanAccessRestrictor<RestrictClasses> {

	@Override
	public boolean isApplicable(RestrictClasses annotation) {
		return annotation.value().length > 0;
	}

	@Override
	public boolean restrictsAccess(RestrictClasses annotation, DependencyConsumer consumer) {
		Class<?> consumerClass = consumer.getConsumerClass();
		Class<?>[] allowedClasses = annotation.value();
		for (Class<?> allowedClass : allowedClasses)
			if (allowedClass.isAssignableFrom(consumerClass))
				return true;
		return false;
	}

}
