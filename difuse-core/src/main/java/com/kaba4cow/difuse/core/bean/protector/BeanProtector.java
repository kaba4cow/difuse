package com.kaba4cow.difuse.core.bean.protector;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import com.kaba4cow.difuse.core.bean.access.provider.BeanAccessProviderEntry;
import com.kaba4cow.difuse.core.bean.access.provider.BeanAccessProvider;
import com.kaba4cow.difuse.core.dependency.DependencyConsumer;

public class BeanProtector {

	private final List<BeanAccessProviderEntry<?>> entries = new ArrayList<>();

	private boolean locked = false;

	BeanProtector() {}

	public boolean allowsAccess(DependencyConsumer consumer) {
		boolean restricting = false;
		for (BeanAccessProviderEntry<?> entry : entries)
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
		entries.add(new BeanAccessProviderEntry<>(annotation, accessProvider));
	}

	public void lock() {
		locked = true;
	}

}
