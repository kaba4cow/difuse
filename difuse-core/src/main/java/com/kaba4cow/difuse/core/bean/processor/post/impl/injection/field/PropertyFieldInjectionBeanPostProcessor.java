package com.kaba4cow.difuse.core.bean.processor.post.impl.injection.field;

import java.lang.reflect.Field;

import com.kaba4cow.difuse.core.annotation.dependency.Property;
import com.kaba4cow.difuse.core.bean.processor.post.phase.FieldInjectionBeanPostProcessor;
import com.kaba4cow.difuse.core.dependency.provider.DependencyProviderSession;

public class PropertyFieldInjectionBeanPostProcessor extends FieldInjectionBeanPostProcessor {

	@Override
	protected Object getFieldValue(Field field, DependencyProviderSession session) {
		return session.provideDependency(field, field.getGenericType());
	}

	@Override
	protected boolean filterField(Field field) {
		return field.isAnnotationPresent(Property.class);
	}

}
