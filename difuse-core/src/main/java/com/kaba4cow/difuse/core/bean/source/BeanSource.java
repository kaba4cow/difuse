package com.kaba4cow.difuse.core.bean.source;

import java.lang.reflect.AnnotatedElement;
import java.util.Objects;

import com.kaba4cow.difuse.core.bean.protector.BeanProtector;
import com.kaba4cow.difuse.core.context.Context;
import com.kaba4cow.difuse.core.dependency.DependencyConsumer;
import com.kaba4cow.difuse.core.scope.Scope;
import com.kaba4cow.difuse.core.scope.support.ScopeRegistry;

public abstract class BeanSource<T extends AnnotatedElement> implements DependencyConsumer {

	private final Context context;

	private final T sourceElement;

	private final Class<?> beanClass;

	private final Class<?> declaringClass;

	private final BeanSourceInfo info;

	private final BeanProtector beanProtector;

	private final Scope scope;

	protected BeanSource(//
			Context context, //
			T sourceElement, //
			Class<?> beanClass, //
			Class<?> declaringClass, //
			BeanProtector beanProtector, //
			ScopeRegistry scopeRegistry) {
		this.context = context;
		this.sourceElement = sourceElement;
		this.beanClass = beanClass;
		this.declaringClass = declaringClass;
		this.info = new BeanSourceInfo(this);
		this.beanProtector = beanProtector;
		this.scope = scopeRegistry.getScope(this);
	}

	@Override
	public Context getContext() {
		return context;
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
	public int hashCode() {
		return Objects.hash(beanClass, declaringClass, sourceElement);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BeanSource<?> other = (BeanSource<?>) obj;
		return Objects.equals(beanClass, other.beanClass) && Objects.equals(declaringClass, other.declaringClass)
				&& Objects.equals(sourceElement, other.sourceElement);
	}

	@Override
	public String toString() {
		return String.format(
				"BeanSource [sourceElement=%s, beanClass=%s, declaringClass=%s, info=%s, beanProtector=%s, scope=%s, contextSource=%s]",
				sourceElement, beanClass, declaringClass, info, beanProtector, scope, context);
	}

}
