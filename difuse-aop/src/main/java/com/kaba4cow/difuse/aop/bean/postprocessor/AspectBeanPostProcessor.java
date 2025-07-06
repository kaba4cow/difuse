package com.kaba4cow.difuse.aop.bean.postprocessor;

import com.kaba4cow.difuse.aop.advisor.support.AdvisorRegistry;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.bean.BeanLifecyclePhase;
import com.kaba4cow.difuse.core.bean.postprocessor.BeanPostProcessor;
import com.kaba4cow.difuse.core.bean.provider.BeanProvider;
import com.kaba4cow.difuse.core.dependency.provider.DependencyProviderSession;

public class AspectBeanPostProcessor implements BeanPostProcessor {

	@Provided
	private AdvisorRegistry advisorRegistry;

	@Override
	public BeanLifecyclePhase getLifecyclePhase() {
		return BeanLifecyclePhase.PROXY_WRAPPING;
	}

	@Override
	public Object process(Object bean, BeanProvider<?> beanProvider, DependencyProviderSession session) {
		return bean;
	}

}
