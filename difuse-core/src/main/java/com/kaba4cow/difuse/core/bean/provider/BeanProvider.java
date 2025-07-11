package com.kaba4cow.difuse.core.bean.provider;

import java.util.Objects;

import com.kaba4cow.difuse.core.DifuseException;
import com.kaba4cow.difuse.core.bean.source.BeanSource;
import com.kaba4cow.difuse.core.dependency.DependencyConsumer;
import com.kaba4cow.difuse.core.dependency.provider.DependencyProvider;
import com.kaba4cow.difuse.core.dependency.provider.DependencyProviderSession;

public abstract class BeanProvider<T extends BeanSource<?>> {

	private final T beanSource;

	private final DependencyProvider dependencyProvider;

	public BeanProvider(T beanSource, DependencyProvider dependencyProvider) {
		this.beanSource = beanSource;
		this.dependencyProvider = dependencyProvider;
	}

	protected abstract Object createBean(DependencyProviderSession session);

	public Object createBean() {
		DependencyProviderSession session = new DependencyProviderSession(dependencyProvider, beanSource);
		return createBean(session);
	}

	public Object provideProtectedBean(DependencyConsumer dependencyConsumer) {
		if (!beanSource.getBeanProtector().allowsAccess(dependencyConsumer))
			throw new DifuseException(String.format("DependencyConsumer %s cannot access protected bean %s",
					dependencyConsumer.getConsumerClass().getName(), beanSource.getBeanClass().getName()));
		else
			return provideBean();
	}

	public Object provideBean() {
		return beanSource.getScope().getBean(this);
	}

	public T getBeanSource() {
		return beanSource;
	}

	@Override
	public int hashCode() {
		return Objects.hash(beanSource);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BeanProvider<?> other = (BeanProvider<?>) obj;
		return Objects.equals(beanSource, other.beanSource);
	}

	@Override
	public String toString() {
		return String.format("BeanProvider [beanSource=%s]", beanSource);
	}

}
