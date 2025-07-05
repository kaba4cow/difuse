package com.kaba4cow.difuse.core.scope.handler.impl;

import com.kaba4cow.difuse.core.bean.provider.BeanProvider;
import com.kaba4cow.difuse.core.scope.handler.Scope;

public class PrototypeScope extends Scope {

	@Override
	public Object requestBean(BeanProvider<?> beanProvider) {
		return beanProvider.createBean();
	}

	@Override
	protected void cleanUp() {}

}
