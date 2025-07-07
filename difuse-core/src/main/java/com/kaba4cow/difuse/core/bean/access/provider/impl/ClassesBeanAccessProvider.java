package com.kaba4cow.difuse.core.bean.access.provider.impl;

import com.kaba4cow.difuse.core.annotation.access.AllowClasses;
import com.kaba4cow.difuse.core.bean.access.provider.BeanAccessProvider;
import com.kaba4cow.difuse.core.dependency.DependencyConsumer;

public class ClassesBeanAccessProvider implements BeanAccessProvider<AllowClasses> {

	@Override
	public boolean isApplicable(AllowClasses annotation) {
		return annotation.value().length > 0;
	}

	@Override
	public boolean allowsAccess(AllowClasses annotation, DependencyConsumer consumer) {
		Class<?> consumerClass = consumer.getConsumerClass();
		Class<?>[] allowedClasses = annotation.value();
		for (Class<?> allowedClass : allowedClasses)
			if (allowedClass.isAssignableFrom(consumerClass))
				return true;
		return false;
	}

}
