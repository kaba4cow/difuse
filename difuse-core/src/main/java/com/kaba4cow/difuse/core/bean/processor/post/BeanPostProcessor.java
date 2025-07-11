package com.kaba4cow.difuse.core.bean.processor.post;

import com.kaba4cow.difuse.core.bean.processor.BeanProcessor;
import com.kaba4cow.difuse.core.bean.provider.impl.ClassBeanProvider;
import com.kaba4cow.difuse.core.dependency.provider.DependencyProviderSession;

public interface BeanPostProcessor extends BeanProcessor, Comparable<BeanPostProcessor> {

	@Override
	default int compareTo(BeanPostProcessor other) {
		return getLifecyclePhase().compareTo(other.getLifecyclePhase());
	}

	BeanLifecyclePhase getLifecyclePhase();

	Object postProcess(Object bean, ClassBeanProvider beanProvider, DependencyProviderSession session);

}
