package com.kaba4cow.difuse.aspects.advisor.support;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
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
			invokeAdvices(signature, AdviceType.BEFORE, args);
			Object result = invokeAroundAdvices(signature, method, args);
			invokeAdvices(signature, AdviceType.AFTER, args);
			return result;
		}

		private void invokeAdvices(MethodSignature signature, AdviceType adviceType, Object[] args) throws Exception {
			Set<Advisor> advisors = getAdvisors(signature, adviceType);
			for (Advisor advisor : advisors)
				invokeAdvice(advisor, new JoinPoint(bean, signature, args));
		}

		private Object invokeAroundAdvices(MethodSignature signature, Method method, Object[] args) throws Exception {
			Set<Advisor> advisors = getAdvisors(signature, AdviceType.AROUND);
			if (advisors.isEmpty())
				return method.invoke(bean, args);
			Object result = null;
			for (Advisor advisor : advisors)
				result = invokeAdvice(advisor, new ProceedingJoinPoint(bean, method, args));
			return result;
		}

		private Set<Advisor> getAdvisors(MethodSignature signature, AdviceType adviceType) {
			return advisors.get(signature).stream()//
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
