package com.kaba4cow.difuse.core.bean.provider.impl;

import java.lang.reflect.Method;

import com.kaba4cow.difuse.core.bean.provider.BeanProvider;
import com.kaba4cow.difuse.core.bean.provider.BeanProviderException;
import com.kaba4cow.difuse.core.bean.source.impl.MethodBeanSource;
import com.kaba4cow.difuse.core.dependency.provider.DependencyProvider;
import com.kaba4cow.difuse.core.dependency.provider.DependencyProviderSession;

public class MethodBeanProvider extends BeanProvider<MethodBeanSource> {

	private final ClassBeanProvider ownerBeanProvider;

	public MethodBeanProvider(//
			MethodBeanSource beanSource, //
			DependencyProvider dependencyProvider, //
			ClassBeanProvider ownerBeanProvider) {
		super(beanSource, dependencyProvider);
		this.ownerBeanProvider = ownerBeanProvider;
	}

	@Override
	protected Object createBean(DependencyProviderSession session) {
		try {
			Method method = getBeanSource().getSourceElement();
			Object ownerBean = ownerBeanProvider.provideBean();
			return method.invoke(ownerBean, session.provideDependencies(method));
		} catch (Exception exception) {
			throw new BeanProviderException(
					String.format("Could not create method bean of type %s", getBeanSource().getBeanClass()), exception);
		}
	}

}
