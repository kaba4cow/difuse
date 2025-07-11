package com.kaba4cow.difuse.aspects.advisor.support;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.kaba4cow.difuse.aspects.advisor.Advisor;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.Accessible;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.bean.source.impl.ClassBeanSource;
import com.kaba4cow.difuse.core.util.MethodSignature;
import com.kaba4cow.difuse.core.util.reflections.MethodScanner;

@Accessible
@SystemBean
public class AdvisorWrapper {

	@Provided
	private AdvisorRegistry advisorRegistry;

	@Provided
	private AdvisorProxyFactory advisorProxyFactory;

	public Object wrapBean(Object bean, ClassBeanSource beanSource) {
		Map<MethodSignature, Set<Advisor>> advisors = getAdvisors(beanSource);
		return advisors.isEmpty()//
				? bean//
				: advisorProxyFactory.createAdvisorProxy(bean, beanSource, advisors);
	}

	private Map<MethodSignature, Set<Advisor>> getAdvisors(ClassBeanSource beanSource) {
		Set<Method> methods = MethodScanner.of(beanSource.getDeclaringClass()).findMethods();
		Map<MethodSignature, Set<Advisor>> advisors = new HashMap<>();
		for (Method method : methods) {
			Set<Advisor> matchingAdvisors = getMatchingAdvisors(method, beanSource);
			if (!matchingAdvisors.isEmpty())
				advisors.put(MethodSignature.of(method), matchingAdvisors);
		}
		return advisors;
	}

	private Set<Advisor> getMatchingAdvisors(Method method, ClassBeanSource beanSource) {
		return advisorRegistry.getAdvisors().stream()//
				.filter(advisor -> advisor.matches(method, beanSource))//
				.collect(Collectors.toSet());
	}

}
