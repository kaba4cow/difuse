package com.kaba4cow.difuse.aspects.advisor.support;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Set;

import com.kaba4cow.difuse.aspects.advisor.AdviceType;
import com.kaba4cow.difuse.aspects.advisor.Advisor;
import com.kaba4cow.difuse.aspects.advisor.AdvisorException;
import com.kaba4cow.difuse.aspects.annotation.advice.After;
import com.kaba4cow.difuse.aspects.annotation.advice.Around;
import com.kaba4cow.difuse.aspects.annotation.advice.Before;
import com.kaba4cow.difuse.aspects.methodfilter.MethodFilter;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.Accessible;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.bean.source.impl.ClassBeanSource;
import com.kaba4cow.difuse.core.system.bean.SystemBeanInjector;
import com.kaba4cow.difuse.core.util.reflections.ConstructorScanner;
import com.kaba4cow.difuse.core.util.reflections.MethodScanner;

@Accessible
@SystemBean
public class AdvisorFactory {

	@Provided
	private AdvisorRegistry advisorRegistry;

	@Provided
	private SystemBeanInjector beanInjector;

	public void createAdvisor(ClassBeanSource beanSource) {
		Object aspectInstance = createAspectInstance(beanSource);
		Set<Method> methods = MethodScanner.of(beanSource.getBeanClass()).findMethods();
		for (Method method : methods) {
			AdviceType adviceType = resolveAdviceType(method);
			if (Objects.isNull(adviceType))
				continue;
			MethodFilter filter = new MethodFilter(method);
			Advisor advisor = new Advisor(beanSource, aspectInstance, method, adviceType, filter);
			advisorRegistry.registerAdvisor(advisor);
		}
	}

	private AdviceType resolveAdviceType(Method method) {
		if (method.isAnnotationPresent(Before.class))
			return AdviceType.BEFORE;
		if (method.isAnnotationPresent(After.class))
			return AdviceType.AFTER;
		if (method.isAnnotationPresent(Around.class))
			return AdviceType.AROUND;
		return null;
	}

	private Object createAspectInstance(ClassBeanSource beanSource) {
		Class<?> type = beanSource.getBeanClass();
		try {
			Object instance = ConstructorScanner.of(type).findNoArgsConstructor().newInstance();
			beanInjector.injectDependencies(instance);
			return instance;
		} catch (Exception exception) {
			throw new AdvisorException(String.format("Could not create aspect instance of type %s", type.getName()), exception);
		}
	}

}
