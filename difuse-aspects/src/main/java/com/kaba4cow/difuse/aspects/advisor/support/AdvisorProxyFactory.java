package com.kaba4cow.difuse.aspects.advisor.support;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.kaba4cow.difuse.aspects.advisor.AdviceType;
import com.kaba4cow.difuse.aspects.advisor.Advisor;
import com.kaba4cow.difuse.aspects.joinpoint.JoinPoint;
import com.kaba4cow.difuse.aspects.joinpoint.ProceedingJoinPoint;
import com.kaba4cow.difuse.core.annotation.system.Accessible;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.bean.source.impl.ClassBeanSource;
import com.kaba4cow.difuse.core.util.ProxyFactory;

@Accessible
@SystemBean
public class AdvisorProxyFactory {

	public Object createAdvisorProxy(Object bean, ClassBeanSource beanSource, Map<Method, Set<Advisor>> advisors) {
		return ProxyFactory.createProxy(//
				beanSource.getBeanClass(), //
				new AdvisorInvocationHandler(bean, advisors)//
		);
	}

	private static class AdvisorInvocationHandler implements InvocationHandler {

		private final Object bean;

		private final Map<Method, Set<Advisor>> advisors;

		public AdvisorInvocationHandler(Object bean, Map<Method, Set<Advisor>> advisors) {
			this.bean = bean;
			this.advisors = advisors;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			Set<Advisor> methodAdvisors = advisors.getOrDefault(method, Collections.emptySet());

			for (Advisor advisor : getAdvisors(methodAdvisors, AdviceType.BEFORE))
				invokeAdvice(advisor, new JoinPoint(bean, method, args));

			Object result = null;
			boolean proceedCalled = false;

			for (Advisor advisor : getAdvisors(methodAdvisors, AdviceType.AROUND)) {
				result = invokeAround(advisor, new ProceedingJoinPoint(bean, method, args));
				proceedCalled = true;
			}

			if (!proceedCalled)
				result = method.invoke(bean, args);

			for (Advisor advisor : getAdvisors(methodAdvisors, AdviceType.AFTER))
				invokeAdvice(advisor, new JoinPoint(bean, method, args));

			return result;
		}

		private Set<Advisor> getAdvisors(Set<Advisor> advisors, AdviceType adviceType) {
			return advisors.stream()//
					.filter(advisor -> advisor.getAdviceType() == adviceType)//
					.collect(Collectors.toSet());
		}

		private void invokeAdvice(Advisor advisor, JoinPoint joinPoint) throws Exception {
			Method method = advisor.getAdviceMethod();
			method.setAccessible(true);
			method.invoke(advisor.getAspectInstance(), joinPoint);
		}

		private Object invokeAround(Advisor advisor, ProceedingJoinPoint joinPoint) throws Exception {
			Method method = advisor.getAdviceMethod();
			method.setAccessible(true);
			return method.invoke(advisor.getAspectInstance(), joinPoint);
		}

	}

}
