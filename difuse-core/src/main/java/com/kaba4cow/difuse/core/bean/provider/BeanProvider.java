package com.kaba4cow.difuse.core.bean.provider;

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
	public String toString() {
		return String.format("BeanProvider [beanSource=%s]", beanSource);
	}

}
