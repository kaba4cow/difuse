package com.kaba4cow.difuse.core.bean.protector;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import com.kaba4cow.difuse.core.bean.access.provider.BeanAccessProvider;
import com.kaba4cow.difuse.core.bean.access.provider.BeanAccessProviderEntry;
import com.kaba4cow.difuse.core.bean.access.restrictor.BeanAccessRestrictor;
import com.kaba4cow.difuse.core.bean.access.restrictor.BeanAccessRestrictorEntry;
import com.kaba4cow.difuse.core.dependency.DependencyConsumer;

public class BeanProtector {

	private final List<BeanAccessProviderEntry<?>> accessProviders = new ArrayList<>();

	private final List<BeanAccessRestrictorEntry<?>> accessRestrictors = new ArrayList<>();

	BeanProtector() {}

	public boolean allowsAccess(DependencyConsumer consumer) {
		boolean restricting = false;
		for (BeanAccessProviderEntry<?> entry : accessProviders)
			if (entry.isApplicable()) {
				restricting = true;
				if (entry.allowsAccess(consumer))
					return true;
			}
		for (BeanAccessRestrictorEntry<?> entry : accessRestrictors)
			if (entry.isApplicable()) {
				restricting = true;
				if (entry.restrictsAccess(consumer))
					return false;
			}
		return !restricting;
	}

	public <T extends Annotation> void addAccessProvider(T annotation, BeanAccessProvider<T> accessProvider) {
		accessProviders.add(new BeanAccessProviderEntry<>(annotation, accessProvider));
	}

	public <T extends Annotation> void addAccessRestrictor(T annotation, BeanAccessRestrictor<T> accessRestrictor) {
		accessRestrictors.add(new BeanAccessRestrictorEntry<>(annotation, accessRestrictor));
	}

}
