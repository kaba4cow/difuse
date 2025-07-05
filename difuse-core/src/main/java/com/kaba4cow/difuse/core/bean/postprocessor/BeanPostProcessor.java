package com.kaba4cow.difuse.core.bean.postprocessor;

import com.kaba4cow.difuse.core.bean.BeanLifecyclePhase;
import com.kaba4cow.difuse.core.bean.provider.BeanProvider;
import com.kaba4cow.difuse.core.dependency.provider.DependencyProviderSession;

public interface BeanPostProcessor {

	BeanLifecyclePhase getLifecyclePhase();

	Object process(Object bean, BeanProvider<?> beanProvider, DependencyProviderSession session);

}
