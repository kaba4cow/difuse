package com.kaba4cow.difuse.core.bean.processor.post.impl.injection.field;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.stream.Collectors;

import com.kaba4cow.difuse.core.bean.processor.post.BeanLifecyclePhase;
import com.kaba4cow.difuse.core.bean.processor.post.BeanPostProcessor;
import com.kaba4cow.difuse.core.bean.source.impl.ClassBeanSource;
import com.kaba4cow.difuse.core.util.reflections.FieldScanner;

public abstract class FieldInjectionBeanPostProcessor implements BeanPostProcessor {

	protected abstract boolean filterField(Field field);

	protected final Set<Field> findTargetFields(ClassBeanSource beanSource) {
		return FieldScanner.of(beanSource.getBeanClass()).findFields().stream()//
				.filter(field -> !Modifier.isStatic(field.getModifiers()))//
				.filter(field -> !Modifier.isFinal(field.getModifiers()))//
				.filter(this::filterField)//
				.collect(Collectors.toSet());
	}

	@Override
	public final BeanLifecyclePhase getLifecyclePhase() {
		return BeanLifecyclePhase.FIELD_INJECTION;
	}

}
