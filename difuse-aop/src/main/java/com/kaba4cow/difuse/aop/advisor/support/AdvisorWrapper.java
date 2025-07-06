package com.kaba4cow.difuse.aop.advisor.support;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.kaba4cow.difuse.aop.advisor.Advisor;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.Accessible;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.bean.source.impl.ClassBeanSource;
import com.kaba4cow.difuse.core.util.reflections.MethodScanner;

@Accessible
@SystemBean
public class AdvisorWrapper {

	@Provided
	private AdvisorRegistry advisorRegistry;

	@Provided
	private AdvisorProxyFactory advisorProxyFactory;

	public Object wrapBean(Object bean, ClassBeanSource beanSource) {
		Map<Method, Set<Advisor>> advisors = getAdvisors(beanSource);
		return advisors.isEmpty()//
				? bean//
				: advisorProxyFactory.createAdvisorProxy(bean, beanSource, advisors);
	}

	private Map<Method, Set<Advisor>> getAdvisors(ClassBeanSource beanSource) {
		Set<Method> methods = MethodScanner.of(beanSource.getDeclaringClass()).findMethods();
		Map<Method, Set<Advisor>> advisors = new HashMap<>();
		for (Method method : methods) {
			Set<Advisor> matchingAdvisors = advisorRegistry.getAdvisors().stream()//
					.filter(advisor -> advisor.matches(method, beanSource))//
					.collect(Collectors.toSet());
			if (!matchingAdvisors.isEmpty())
				advisors.put(method, matchingAdvisors);
		}
		return advisors;
	}

}
