package com.kaba4cow.difuse.core.bean.processor.post.impl;

import java.lang.reflect.Field;
import java.util.Set;

import com.kaba4cow.difuse.core.annotation.bean.Lazy;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.bean.processor.post.BeanLifecyclePhase;
import com.kaba4cow.difuse.core.bean.processor.post.BeanPostProcessor;
import com.kaba4cow.difuse.core.bean.processor.post.BeanPostProcessorException;
import com.kaba4cow.difuse.core.bean.provider.impl.ClassBeanProvider;
import com.kaba4cow.difuse.core.bean.source.impl.ClassBeanSource;
import com.kaba4cow.difuse.core.dependency.provider.DependencyProviderSession;

public class EagerProvidedFieldInjectionBeanPostProcessor implements BeanPostProcessor {

	@Override
	public BeanLifecyclePhase getLifecyclePhase() {
		return BeanLifecyclePhase.FIELD_INJECTION;
	}

	@Override
	public Object postProcess(Object bean, ClassBeanProvider beanProvider, DependencyProviderSession session) {
		ClassBeanSource beanSource = beanProvider.getBeanSource();
		findEagerFields(beanSource).forEach(field -> {
			try {
				field.setAccessible(true);
				field.set(bean, session.provideDependency(field, field.getGenericType()));
			} catch (Exception exception) {
				throw new BeanPostProcessorException(String.format("Could not initialize field %s for bean of type %s",
						field.getName(), beanSource.getBeanClass().getName()), exception);
			}
		});
		return bean;
	}

	private Set<Field> findEagerFields(ClassBeanSource beanSource) {
		return BeanPostProcessorReflections.findFields(beanSource, //
				field -> !field.isAnnotationPresent(Lazy.class), //
				field -> field.isAnnotationPresent(Provided.class)//
		);
	}

}
