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
import com.kaba4cow.difuse.core.util.MethodSignature;
import com.kaba4cow.difuse.core.util.ProxyFactory;

@Accessible
@SystemBean
public class AdvisorProxyFactory {

	public Object createAdvisorProxy(Object bean, ClassBeanSource beanSource, Map<MethodSignature, Set<Advisor>> advisors) {
		return ProxyFactory.createProxy(//
				beanSource.getBeanClass(), //
				new AdvisorInvocationHandler(bean, advisors)//
		);
	}

	private static class AdvisorInvocationHandler implements InvocationHandler {

		private final Object bean;

		private final Map<MethodSignature, Set<Advisor>> advisors;

		public AdvisorInvocationHandler(Object bean, Map<MethodSignature, Set<Advisor>> advisors) {
			this.bean = bean;
			this.advisors = advisors;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			MethodSignature signature = MethodSignature.of(method);
			if (!advisors.containsKey(signature))
				return method.invoke(bean, args);

			Set<Advisor> methodAdvisors = advisors.getOrDefault(signature, Collections.emptySet());

			for (Advisor advisor : getAdvisors(methodAdvisors, AdviceType.BEFORE))
				invokeAdvice(advisor, new JoinPoint(bean, method, args));

			Object result = null;
			boolean proceedCalled = false;

			for (Advisor advisor : getAdvisors(methodAdvisors, AdviceType.AROUND)) {
				result = invokeAdvice(advisor, new ProceedingJoinPoint(bean, method, args));
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

		private Object invokeAdvice(Advisor advisor, JoinPoint joinPoint) throws Exception {
			Object instance = advisor.getAspectInstance();
			Method method = advisor.getAdviceMethod();
			method.setAccessible(true);
			return method.invoke(instance, joinPoint);
		}

	}

}
