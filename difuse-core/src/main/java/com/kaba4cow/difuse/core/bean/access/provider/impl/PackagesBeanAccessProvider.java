package com.kaba4cow.difuse.core.bean.access.provider.impl;

import com.kaba4cow.difuse.core.annotation.access.provider.AllowPackages;
import com.kaba4cow.difuse.core.bean.access.provider.BeanAccessProvider;
import com.kaba4cow.difuse.core.dependency.DependencyConsumer;

public class PackagesBeanAccessProvider implements BeanAccessProvider<AllowPackages> {

	@Override
	public boolean isApplicable(AllowPackages annotation) {
		return annotation.value().length > 0;
	}

	@Override
	public boolean allowsAccess(AllowPackages annotation, DependencyConsumer consumer) {
		String consumerPackage = consumer.getConsumerClass().getPackage().getName();
		String[] allowedPackages = annotation.value();
		for (String allowedPackage : allowedPackages)
			if (consumerPackage.equals(allowedPackage))
				return true;
		return false;
	}

}
