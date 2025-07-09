package com.kaba4cow.difuse.aspects.bean.processor.post;

import com.kaba4cow.difuse.aspects.advisor.support.AdvisorRegistry;
import com.kaba4cow.difuse.aspects.advisor.support.AdvisorWrapper;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.bean.processor.post.BeanLifecyclePhase;
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
	public Object postProcess(Object bean, ClassBeanProvider beanProvider, DependencyProviderSession session) {
		return advisorWrapper.wrapBean(bean, beanProvider.getBeanSource());
	}

}
