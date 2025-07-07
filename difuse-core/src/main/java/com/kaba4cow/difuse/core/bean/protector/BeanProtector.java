package com.kaba4cow.difuse.core.bean.protector;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import com.kaba4cow.difuse.core.bean.access.provider.BeanAccessProvider;
import com.kaba4cow.difuse.core.dependency.DependencyConsumer;

public class BeanProtector {

	private final List<AccessProviderEntry<?>> entries = new ArrayList<>();

	private boolean locked = false;

	BeanProtector() {}

	public boolean canBeAccessedBy(DependencyConsumer consumer) {
		boolean restricting = false;
		for (AccessProviderEntry<?> entry : entries)
			if (entry.isApplicable()) {
				restricting = true;
				if (entry.allowsAccess(consumer))
					return true;
			}
		return !restricting;
	}

	public <T extends Annotation> void addAccessProvider(T annotation, BeanAccessProvider<T> accessProvider) {
		if (locked)
			throw new BeanProtectorException("Cannot add access provider: BeanProtector already locked");
		entries.add(new AccessProviderEntry<>(annotation, accessProvider));
	}

	public void lock() {
		locked = true;
	}

	private static class AccessProviderEntry<T extends Annotation> {

		private final T annotation;

		private final BeanAccessProvider<T> accessProvider;

		private AccessProviderEntry(T annotation, BeanAccessProvider<T> accessProvider) {
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

}
