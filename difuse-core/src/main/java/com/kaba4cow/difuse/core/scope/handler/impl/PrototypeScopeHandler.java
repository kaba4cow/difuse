package com.kaba4cow.difuse.core.scope.handler.impl;

import com.kaba4cow.difuse.core.bean.provider.BeanProvider;
import com.kaba4cow.difuse.core.scope.handler.ScopeHandler;

public class PrototypeScopeHandler extends ScopeHandler {

	@Override
	public Object requestBean(BeanProvider<?> beanProvider) {
		return beanProvider.createBean();
	}

	@Override
	protected void cleanUp() {}

}
