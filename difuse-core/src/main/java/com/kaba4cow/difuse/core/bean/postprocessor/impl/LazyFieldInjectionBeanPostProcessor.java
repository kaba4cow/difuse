package com.kaba4cow.difuse.core.bean.postprocessor.impl;

import java.lang.reflect.Field;
import java.util.Set;

import com.kaba4cow.difuse.core.annotation.bean.Lazy;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.bean.BeanLifecyclePhase;
import com.kaba4cow.difuse.core.bean.postprocessor.BeanPostProcessor;
import com.kaba4cow.difuse.core.bean.postprocessor.BeanPostProcessorException;
import com.kaba4cow.difuse.core.bean.provider.BeanProvider;
import com.kaba4cow.difuse.core.bean.source.BeanSource;
import com.kaba4cow.difuse.core.dependency.provider.DependencyProviderSession;
import com.kaba4cow.difuse.core.util.ProxyFactory;

public class LazyFieldInjectionBeanPostProcessor implements BeanPostProcessor {

	@Override
	public BeanLifecyclePhase getLifecyclePhase() {
		return BeanLifecyclePhase.FIELD_INJECTION;
	}

	@Override
	public Object process(Object bean, BeanProvider<?> beanProvider, DependencyProviderSession session) {
		BeanSource<?> beanSource = beanProvider.getBeanSource();
		findLazyFields(beanSource).forEach(field -> {
			try {
				field.setAccessible(true);
				field.set(bean, createProxy(field, session));
			} catch (Exception exception) {
				throw new BeanPostProcessorException(String.format("Could not initialize lazy field %s for bean of type %s",
						field.getName(), beanSource.getBeanClass().getClass()), exception);
			}
		});
		return bean;
	}

	private Object createProxy(Field field, DependencyProviderSession session) {
		return ProxyFactory.createLazyProxy(field.getType(), () -> session.provideDependency(field, field.getGenericType()));
	}

	private Set<Field> findLazyFields(BeanSource<?> beanSource) {
		return BeanPostProcessorReflections.findFields(beanSource, //
				field -> field.isAnnotationPresent(Lazy.class), //
				field -> field.isAnnotationPresent(Provided.class)//
		);
	}

}
