package com.kaba4cow.difuse.core.bean.access.restrictor;

import java.lang.annotation.Annotation;

import com.kaba4cow.difuse.core.dependency.DependencyConsumer;

public class BeanAccessRestrictorEntry<T extends Annotation> {

	private final T annotation;

	private final BeanAccessRestrictor<T> accessRestrictor;

	public BeanAccessRestrictorEntry(T annotation, BeanAccessRestrictor<T> accessRestrictor) {
		this.annotation = annotation;
		this.accessRestrictor = accessRestrictor;
	}

	public boolean isApplicable() {
		return accessRestrictor.isApplicable(annotation);
	}

	public boolean restrictsAccess(DependencyConsumer consumer) {
		return accessRestrictor.restrictsAccess(annotation, consumer);
	}

}
