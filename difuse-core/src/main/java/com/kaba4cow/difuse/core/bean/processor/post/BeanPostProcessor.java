package com.kaba4cow.difuse.core.bean.processor.post;

import com.kaba4cow.difuse.core.bean.BeanLifecyclePhase;
import com.kaba4cow.difuse.core.bean.processor.BeanProcessor;
import com.kaba4cow.difuse.core.bean.provider.impl.ClassBeanProvider;
import com.kaba4cow.difuse.core.dependency.provider.DependencyProviderSession;

public interface BeanPostProcessor extends BeanProcessor {

	BeanLifecyclePhase getLifecyclePhase();

	Object process(Object bean, ClassBeanProvider beanProvider, DependencyProviderSession session);

}
