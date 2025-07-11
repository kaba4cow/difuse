package com.kaba4cow.difuse.core.scope.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.kaba4cow.difuse.core.bean.provider.BeanProvider;
import com.kaba4cow.difuse.core.scope.Scope;

public class SingletonScope extends Scope {

	private final Map<BeanProvider<?>, Object> map = new ConcurrentHashMap<>();

	@Override
	public Object requestBean(BeanProvider<?> beanProvider) {
		return map.computeIfAbsent(beanProvider, BeanProvider::createBean);
	}

	@Override
	protected void cleanUp() {
		map.clear();
	}

}
