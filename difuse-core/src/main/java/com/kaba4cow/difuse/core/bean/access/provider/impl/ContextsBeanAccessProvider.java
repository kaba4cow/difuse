package com.kaba4cow.difuse.core.bean.access.provider.impl;

import com.kaba4cow.difuse.core.annotation.access.AllowContexts;
import com.kaba4cow.difuse.core.bean.access.provider.BeanAccessProvider;
import com.kaba4cow.difuse.core.dependency.DependencyConsumer;

public class ContextsBeanAccessProvider implements BeanAccessProvider<AllowContexts> {

	@Override
	public boolean isApplicable(AllowContexts annotation) {
		return annotation.value().length > 0;
	}

	@Override
	public boolean allowsAccess(AllowContexts annotation, DependencyConsumer consumer) {
		Class<?> consumerContext = consumer.getContextSource().getSourceClass();
		Class<?>[] allowedContexts = annotation.value();
		for (Class<?> allowedContext : allowedContexts)
			if (consumerContext.equals(allowedContext))
				return true;
		return false;
	}

}
