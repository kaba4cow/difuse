package com.kaba4cow.difuse.core.bean.source;

import java.lang.reflect.AnnotatedElement;

import com.kaba4cow.difuse.core.bean.protector.BeanProtector;
import com.kaba4cow.difuse.core.context.source.ContextSource;
import com.kaba4cow.difuse.core.dependency.DependencyConsumer;
import com.kaba4cow.difuse.core.scope.Scope;
import com.kaba4cow.difuse.core.scope.support.ScopeRegistry;

public abstract class BeanSource<T extends AnnotatedElement> implements DependencyConsumer {

	private final ContextSource contextSource;

	private final T sourceElement;

	private final Class<?> beanClass;

	private final Class<?> declaringClass;

	private final BeanSourceInfo info;

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
		this.info = new BeanSourceInfo(this);
		this.beanProtector = beanProtector;
		this.scope = scopeRegistry.getScope(this);
	}

	public void finishProcessing() {
		beanProtector.lock();
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

	public BeanSourceInfo getInfo() {
		return info;
	}

	public BeanProtector getBeanProtector() {
		return beanProtector;
	}

	public Scope getScope() {
		return scope;
	}

	@Override
	public String toString() {
		return String.format(
				"BeanSource [sourceElement=%s, beanClass=%s, declaringClass=%s, info=%s, beanProtector=%s, scope=%s, contextSource=%s]",
				sourceElement, beanClass, declaringClass, info, beanProtector, scope, contextSource);
	}

}
