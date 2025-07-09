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

	private final List<BeanAccessProviderEntry<?>> providerEntries = new ArrayList<>();

	private final List<BeanAccessRestrictorEntry<?>> restrictorEntries = new ArrayList<>();

	BeanProtector() {}

	public boolean allowsAccess(DependencyConsumer consumer) {
		boolean restricting = false;
		for (BeanAccessProviderEntry<?> entry : providerEntries)
			if (entry.isApplicable()) {
				restricting = true;
				if (entry.allowsAccess(consumer))
					return true;
			}
		for (BeanAccessRestrictorEntry<?> entry : restrictorEntries)
			if (entry.isApplicable()) {
				restricting = true;
				if (entry.restrictsAccess(consumer))
					return false;
			}
		return !restricting;
	}

	public <T extends Annotation> void addAccessProvider(T annotation, BeanAccessProvider<T> accessProvider) {
		providerEntries.add(new BeanAccessProviderEntry<>(annotation, accessProvider));
	}

	public <T extends Annotation> void addAccessRestrictor(T annotation, BeanAccessRestrictor<T> accessRestrictor) {
		restrictorEntries.add(new BeanAccessRestrictorEntry<>(annotation, accessRestrictor));
	}

}
