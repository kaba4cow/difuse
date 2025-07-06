package com.kaba4cow.difuse.aop.advisor;

import java.lang.reflect.Method;

import com.kaba4cow.difuse.aop.methodfilter.MethodFilter;
import com.kaba4cow.difuse.core.bean.source.impl.ClassBeanSource;

public class Advisor {

	private final ClassBeanSource beanSource;

	private final Object aspectInstance;

	private final Method adviceMethod;

	private final AdviceType adviceType;

	private final MethodFilter methodFilter;

	public Advisor(ClassBeanSource beanSource, Object aspectInstance, Method adviceMethod, AdviceType adviceType,
			MethodFilter methodFilter) {
		this.beanSource = beanSource;
		this.aspectInstance = aspectInstance;
		this.adviceMethod = adviceMethod;
		this.adviceType = adviceType;
		this.methodFilter = methodFilter;
	}

	public boolean matches(Method method, ClassBeanSource beanSource) {
		return methodFilter.filter(method, beanSource);
	}

	public ClassBeanSource getBeanSource() {
		return beanSource;
	}

	public Object getAspectInstance() {
		return aspectInstance;
	}

	public Method getAdviceMethod() {
		return adviceMethod;
	}

	public AdviceType getAdviceType() {
		return adviceType;
	}

}
