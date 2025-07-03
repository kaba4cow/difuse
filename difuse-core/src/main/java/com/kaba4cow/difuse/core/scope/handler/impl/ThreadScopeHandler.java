package com.kaba4cow.difuse.core.scope.handler.impl;

import java.util.HashMap;
import java.util.Map;

import com.kaba4cow.difuse.core.bean.provider.BeanProvider;
import com.kaba4cow.difuse.core.scope.handler.ScopeHandler;

public class ThreadScopeHandler extends ScopeHandler {

	private final ThreadLocal<Map<BeanProvider<?>, Object>> maps = ThreadLocal.withInitial(HashMap::new);

	@Override
	public Object requestBean(BeanProvider<?> beanProvider) {
		return maps.get().computeIfAbsent(beanProvider, BeanProvider::createBean);
	}

	@Override
	protected void cleanUp() {
		maps.remove();
	}

}
