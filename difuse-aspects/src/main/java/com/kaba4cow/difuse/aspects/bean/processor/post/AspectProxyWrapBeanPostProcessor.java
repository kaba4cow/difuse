package com.kaba4cow.difuse.aspects.bean.processor.post;

import com.kaba4cow.difuse.aspects.advisor.support.AdvisorRegistry;
import com.kaba4cow.difuse.aspects.advisor.support.AdvisorWrapper;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.bean.processor.post.phase.ProxyWrapBeanPostProcessor;
import com.kaba4cow.difuse.core.bean.provider.impl.ClassBeanProvider;
import com.kaba4cow.difuse.core.dependency.provider.DependencyProviderSession;

public class AspectProxyWrapBeanPostProcessor extends ProxyWrapBeanPostProcessor {

	@Provided
	private AdvisorRegistry advisorRegistry;

	@Provided
	private AdvisorWrapper advisorWrapper;

	@Override
	public boolean isActive() {
		return advisorRegistry.hasAdvisors();
	}

	@Override
	public Object postProcess(Object bean, ClassBeanProvider beanProvider, DependencyProviderSession session) {
		return advisorWrapper.wrapBean(bean, beanProvider.getBeanSource());
	}

}
