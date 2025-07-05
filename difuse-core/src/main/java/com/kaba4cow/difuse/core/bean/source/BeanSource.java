package com.kaba4cow.difuse.core.bean.source;

import java.lang.reflect.AnnotatedElement;

import com.kaba4cow.difuse.core.annotation.bean.Lazy;
import com.kaba4cow.difuse.core.annotation.bean.Named;
import com.kaba4cow.difuse.core.bean.protector.BeanProtector;
import com.kaba4cow.difuse.core.context.source.ContextSource;
import com.kaba4cow.difuse.core.dependency.DependencyConsumer;
import com.kaba4cow.difuse.core.scope.handler.Scope;
import com.kaba4cow.difuse.core.scope.support.ScopeRegistry;

public abstract class BeanSource<T extends AnnotatedElement> implements DependencyConsumer {

	private final ContextSource contextSource;

	private final T sourceElement;

	private final Class<?> beanClass;

	private final Class<?> declaringClass;

	private final BeanProtector beanProtector;

	private final Scope scope;

	protected BeanSource(//
			ContextSource contextSource, //
			T sourceElement, //
			Class<?> beanClass, //
			Class<?> declaringClass, //
			BeanProtector beanProtector, //
			ScopeRegistry scopeRegistry) {
		this.contextSource = contextSource;
		this.sourceElement = sourceElement;
		this.beanClass = beanClass;
		this.declaringClass = declaringClass;
		this.beanProtector = beanProtector;
		this.scope = scopeRegistry.getScope(this);
	}

	@Override
	public ContextSource getContextSource() {
		return contextSource;
	}

	@Override
	public Class<?> getConsumerClass() {
		return getBeanClass();
	}

	public T getSourceElement() {
		return sourceElement;
	}

	public Class<?> getBeanClass() {
		return beanClass;
	}

	public Class<?> getDeclaringClass() {
		return declaringClass;
	}

	public BeanProtector getBeanProtector() {
		return beanProtector;
	}

	public Scope getScope() {
		return scope;
	}

	public String getName() {
		return getSourceElement().isAnnotationPresent(Named.class)//
				? getSourceElement().getAnnotation(Named.class).value()//
				: "";
	}

	public boolean isLazy() {
		return getSourceElement().isAnnotationPresent(Lazy.class);
	}

	@Override
	public String toString() {
		return String.format(
				"BeanSource [sourceElement=%s, beanClass=%s, declaringClass=%s, beanProtector=%s, scope=%s, contextSource=%s]",
				sourceElement, beanClass, declaringClass, beanProtector, scope, contextSource);
	}

}
