package com.kaba4cow.difuse.aop.advisor;

import com.kaba4cow.difuse.core.bean.source.BeanSource;

public class Advisor {

	private final Object aspect;

	private final BeanSource<?> beanSource;

	public Advisor(Object aspect, BeanSource<?> beanSource) {
		this.aspect = aspect;
		this.beanSource = beanSource;
	}

	public BeanSource<?> getBeanSource() {
		return beanSource;
	}

}
