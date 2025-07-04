package com.kaba4cow.difuse.core.bean.source.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.kaba4cow.difuse.core.bean.protector.BeanProtector;
import com.kaba4cow.difuse.core.bean.source.BeanSource;
import com.kaba4cow.difuse.core.context.source.ContextSource;
import com.kaba4cow.difuse.core.scope.support.ScopeRegistry;

public class ClassBeanSource extends BeanSource<Class<?>> {

	private final Set<MethodBeanSource> childBeanSources = new HashSet<>();

	public ClassBeanSource(//
			ContextSource contextSource, //
			Class<?> beanClass, //
			BeanProtector beanProtector, //
			ScopeRegistry scopeRegistry) {
		super(//
				contextSource, //
				beanClass, //
				beanClass, //
				beanClass, //
				beanProtector, //
				scopeRegistry);
	}

	public void addChildBeanSource(MethodBeanSource childBeanSource) {
		childBeanSources.add(childBeanSource);
	}

	public Set<MethodBeanSource> getChildBeanSources() {
		return Collections.unmodifiableSet(childBeanSources);
	}

}
