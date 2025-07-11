package com.kaba4cow.difuse.core.bean.processor.post.impl.injection.field;

import java.lang.reflect.Field;

import com.kaba4cow.difuse.core.annotation.bean.Lazy;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.bean.processor.post.BeanPostProcessorException;
import com.kaba4cow.difuse.core.bean.provider.impl.ClassBeanProvider;
import com.kaba4cow.difuse.core.bean.source.impl.ClassBeanSource;
import com.kaba4cow.difuse.core.dependency.provider.DependencyProviderSession;
import com.kaba4cow.difuse.core.util.ProxyFactory;

public class LazyProvidedFieldInjectionBeanPostProcessor extends FieldInjectionBeanPostProcessor {

	@Override
	protected boolean filterField(Field field) {
		return field.isAnnotationPresent(Lazy.class) && field.isAnnotationPresent(Provided.class);
	}

	@Override
	public Object postProcess(Object bean, ClassBeanProvider beanProvider, DependencyProviderSession session) {
		ClassBeanSource beanSource = beanProvider.getBeanSource();
		findTargetFields(beanSource).forEach(field -> {
			try {
				field.setAccessible(true);
				field.set(bean, createProxy(field, session));
			} catch (Exception exception) {
				throw new BeanPostProcessorException(String.format("Could not initialize lazy field %s for bean of type %s",
						field.getName(), beanSource.getBeanClass().getName()), exception);
			}
		});
		return bean;
	}

	private Object createProxy(Field field, DependencyProviderSession session) {
		return ProxyFactory.createLazyProxy(field.getType(), () -> session.provideDependency(field, field.getGenericType()));
	}

}
