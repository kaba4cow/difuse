package com.kaba4cow.difuse.core.bean.source.impl;

import java.lang.reflect.Method;

import com.kaba4cow.difuse.core.bean.protector.BeanProtector;
import com.kaba4cow.difuse.core.bean.source.BeanSource;
import com.kaba4cow.difuse.core.context.source.ContextSource;
import com.kaba4cow.difuse.core.scope.support.ScopeRegistry;

public class MethodBeanSource extends BeanSource<Method> {

	public MethodBeanSource(//
			ContextSource contextSource, //
			Method beanMethod, //
			BeanProtector beanProtector, //
			ScopeRegistry scopeRegistry, //
			Class<?> declaringClass) {
		super(//
				contextSource, //
				beanMethod, //
				beanMethod.getReturnType(), //
				declaringClass, //
				beanProtector, //
				scopeRegistry);
	}

}
