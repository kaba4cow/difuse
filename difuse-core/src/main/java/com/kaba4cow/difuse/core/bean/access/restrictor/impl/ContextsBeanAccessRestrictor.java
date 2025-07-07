package com.kaba4cow.difuse.core.bean.access.restrictor.impl;

import com.kaba4cow.difuse.core.annotation.access.restrictor.RestrictContexts;
import com.kaba4cow.difuse.core.bean.access.restrictor.BeanAccessRestrictor;
import com.kaba4cow.difuse.core.dependency.DependencyConsumer;

public class ContextsBeanAccessRestrictor implements BeanAccessRestrictor<RestrictContexts> {

	@Override
	public boolean isApplicable(RestrictContexts annotation) {
		return annotation.value().length > 0;
	}

	@Override
	public boolean restrictsAccess(RestrictContexts annotation, DependencyConsumer consumer) {
		Class<?> consumerContext = consumer.getContextSource().getSourceClass();
		Class<?>[] allowedContexts = annotation.value();
		for (Class<?> allowedContext : allowedContexts)
			if (consumerContext.equals(allowedContext))
				return true;
		return false;
	}

}
