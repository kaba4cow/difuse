package com.kaba4cow.difuse.aspects.advisor.support;

import java.lang.reflect.InvocationHandler;
import java.util.Map;
import java.util.Set;

import com.kaba4cow.difuse.aspects.advisor.Advisor;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.Accessible;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.bean.provider.support.BeanProviderRegistry;
import com.kaba4cow.difuse.core.bean.source.impl.ClassBeanSource;
import com.kaba4cow.difuse.core.util.MethodSignature;
import com.kaba4cow.difuse.core.util.ProxyFactory;

@Accessible
@SystemBean
public class AdvisorProxyFactory {

	@Provided
	private BeanProviderRegistry beanProviderRegistry;

	public Object createAdvisorProxy(Object bean, ClassBeanSource beanSource, Map<MethodSignature, Set<Advisor>> advisors) {
		return ProxyFactory.createProxy(//
				beanSource.getBeanClass(), //
				createInvocationHandler(bean, advisors)//
		);
	}

	private InvocationHandler createInvocationHandler(Object bean, Map<MethodSignature, Set<Advisor>> advisors) {
		return new AdvisorInvocationHandler(beanProviderRegistry, bean, advisors);
	}

}
