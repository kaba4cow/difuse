package com.kaba4cow.difuse.core.bean.access.restrictor.impl;

import com.kaba4cow.difuse.core.annotation.access.restrictor.RestrictPackages;
import com.kaba4cow.difuse.core.bean.access.restrictor.BeanAccessRestrictor;
import com.kaba4cow.difuse.core.dependency.DependencyConsumer;

public class PackagesBeanAccessRestrictor implements BeanAccessRestrictor<RestrictPackages> {

	@Override
	public boolean isApplicable(RestrictPackages annotation) {
		return annotation.value().length > 0;
	}

	@Override
	public boolean restrictsAccess(RestrictPackages annotation, DependencyConsumer consumer) {
		String consumerPackage = consumer.getConsumerClass().getPackage().getName();
		String[] allowedPackages = annotation.value();
		for (String allowedPackage : allowedPackages)
			if (consumerPackage.equals(allowedPackage))
				return true;
		return false;
	}

}
