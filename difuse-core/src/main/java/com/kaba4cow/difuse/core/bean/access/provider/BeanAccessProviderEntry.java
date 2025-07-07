package com.kaba4cow.difuse.core.bean.access.provider;

import java.lang.annotation.Annotation;

import com.kaba4cow.difuse.core.dependency.DependencyConsumer;

public class BeanAccessProviderEntry<T extends Annotation> {

	private final T annotation;

	private final BeanAccessProvider<T> accessProvider;

	public BeanAccessProviderEntry(T annotation, BeanAccessProvider<T> accessProvider) {
		this.annotation = annotation;
		this.accessProvider = accessProvider;
	}

	public boolean isApplicable() {
		return accessProvider.isApplicable(annotation);
	}

	public boolean allowsAccess(DependencyConsumer consumer) {
		return accessProvider.allowsAccess(annotation, consumer);
	}

}
