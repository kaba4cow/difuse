package com.kaba4cow.difuse.core.bean.processor.post.impl.injection.field;

import java.lang.reflect.Field;

import com.kaba4cow.difuse.core.annotation.bean.Lazy;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.dependency.provider.DependencyProviderSession;
import com.kaba4cow.difuse.core.util.ProxyFactory;

public class LazyProvidedFieldInjectionBeanPostProcessor extends FieldInjectionBeanPostProcessor {

	@Override
	protected Object getFieldValue(Field field, DependencyProviderSession session) {
		return ProxyFactory.createLazyProxy(field.getType(), () -> session.provideDependency(field, field.getGenericType()));
	}

	@Override
	protected boolean filterField(Field field) {
		return field.isAnnotationPresent(Lazy.class) && field.isAnnotationPresent(Provided.class);
	}

}
