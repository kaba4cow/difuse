package com.kaba4cow.difuse.core.bean.protector;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.HashMap;
import java.util.Map;

import com.kaba4cow.difuse.core.bean.access.provider.BeanAccessProvider;
import com.kaba4cow.difuse.core.dependency.DependencyConsumer;

public class BeanProtector {

	private final Map<Class<? extends Annotation>, Annotation> accessAnnotations = new HashMap<>();

	private final Map<Class<? extends Annotation>, BeanAccessProvider<?>> accessProviders = new HashMap<>();

	private boolean locked = false;

	BeanProtector(AnnotatedElement... elements) {}

	public boolean canBeAccessedBy(DependencyConsumer consumer) {
		return true;
	}

	public void addAccessProvider(Annotation annotation, BeanAccessProvider<?> accessProvider) {
		if (locked)
			throw new BeanProtectorException("Cannot add access provider. BeanProtector already locked");
		Class<? extends Annotation> type = annotation.annotationType();
		accessAnnotations.put(type, annotation);
		accessProviders.put(type, accessProvider);
	}

	public void lock() {
		locked = true;
	}

}
