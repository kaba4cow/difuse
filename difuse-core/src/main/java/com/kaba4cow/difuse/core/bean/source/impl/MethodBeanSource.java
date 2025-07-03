package com.kaba4cow.difuse.core.bean.source.impl;

import java.lang.reflect.Method;

import com.kaba4cow.difuse.core.bean.protector.BeanProtector;
import com.kaba4cow.difuse.core.bean.source.BeanSource;
import com.kaba4cow.difuse.core.context.source.ContextSource;
import com.kaba4cow.difuse.core.scope.support.ScopeHandlerRegistry;

public class MethodBeanSource extends BeanSource<Method> {

	private final ClassBeanSource ownerBeanSource;

	public MethodBeanSource(//
			ContextSource contextSource, //
			Method beanMethod, //
			BeanProtector beanProtector, //
			ScopeHandlerRegistry scopeHandlerRegistry, //
			ClassBeanSource ownerBeanSource) {
		super(//
				contextSource, //
				beanMethod, //
				beanMethod.getReturnType(), //
				ownerBeanSource.getDeclaringClass(), //
				beanProtector, //
				scopeHandlerRegistry);
		this.ownerBeanSource = ownerBeanSource;
		this.ownerBeanSource.addChildBeanSource(this);
	}

	public ClassBeanSource getOwnerBeanSource() {
		return ownerBeanSource;
	}

}
