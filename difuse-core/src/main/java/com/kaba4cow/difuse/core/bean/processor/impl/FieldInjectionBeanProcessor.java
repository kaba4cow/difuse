package com.kaba4cow.difuse.core.bean.processor.impl;

import java.lang.reflect.Field;
import java.util.Set;

import com.kaba4cow.difuse.core.DifuseException;
import com.kaba4cow.difuse.core.annotation.bean.Lazy;
import com.kaba4cow.difuse.core.annotation.dependency.Property;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.bean.BeanLifecyclePhase;
import com.kaba4cow.difuse.core.bean.processor.BeanProcessor;
import com.kaba4cow.difuse.core.bean.provider.BeanProvider;
import com.kaba4cow.difuse.core.bean.source.BeanSource;
import com.kaba4cow.difuse.core.dependency.provider.DependencyProviderSession;

public class FieldInjectionBeanProcessor implements BeanProcessor {

	@Override
	public BeanLifecyclePhase getLifecyclePhase() {
		return BeanLifecyclePhase.FIELD_INJECTION;
	}

	@Override
	public Object process(Object bean, BeanProvider<?> beanProvider, DependencyProviderSession session) {
		BeanSource<?> beanSource = beanProvider.getBeanSource();
		findEagerFields(beanSource).forEach(field -> {
			try {
				field.setAccessible(true);
				field.set(bean, session.provideDependency(field, field.getGenericType()));
			} catch (Exception exception) {
				throw new DifuseException(String.format("Could not initialize field %s for bean of type %s", field.getName(),
						beanSource.getBeanClass().getClass()), exception);
			}
		});
		return bean;
	}

	private Set<Field> findEagerFields(BeanSource<?> beanSource) {
		return BeanProcessorReflections.findFields(beanSource, //
				field -> !field.isAnnotationPresent(Lazy.class), //
				field -> field.isAnnotationPresent(Provided.class) || field.isAnnotationPresent(Property.class)//
		);
	}

}
