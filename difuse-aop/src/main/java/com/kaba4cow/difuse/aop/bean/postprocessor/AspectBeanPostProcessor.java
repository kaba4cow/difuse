package com.kaba4cow.difuse.aop.bean.postprocessor;

import com.kaba4cow.difuse.aop.advisor.support.AdvisorRegistry;
import com.kaba4cow.difuse.aop.advisor.support.AdvisorWrapper;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.bean.BeanLifecyclePhase;
import com.kaba4cow.difuse.core.bean.processor.post.BeanPostProcessor;
import com.kaba4cow.difuse.core.bean.provider.impl.ClassBeanProvider;
import com.kaba4cow.difuse.core.dependency.provider.DependencyProviderSession;

public class AspectBeanPostProcessor implements BeanPostProcessor {

	@Provided
	private AdvisorRegistry advisorRegistry;

	@Provided
	private AdvisorWrapper advisorWrapper;

	@Override
	public BeanLifecyclePhase getLifecyclePhase() {
		return BeanLifecyclePhase.PROXY_WRAPPING;
	}

	@Override
	public boolean isActive() {
		return advisorRegistry.hasAdvisors();
	}

	@Override
	public Object process(Object bean, ClassBeanProvider beanProvider, DependencyProviderSession session) {
		return advisorWrapper.wrapBean(bean, beanProvider.getBeanSource());
	}

}
