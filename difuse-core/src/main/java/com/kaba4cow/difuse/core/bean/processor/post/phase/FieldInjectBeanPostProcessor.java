package com.kaba4cow.difuse.core.bean.processor.post.phase;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.stream.Collectors;

import com.kaba4cow.difuse.core.bean.processor.post.BeanLifecyclePhase;
import com.kaba4cow.difuse.core.bean.processor.post.BeanPostProcessor;
import com.kaba4cow.difuse.core.bean.processor.post.BeanPostProcessorException;
import com.kaba4cow.difuse.core.bean.provider.impl.ClassBeanProvider;
import com.kaba4cow.difuse.core.bean.source.impl.ClassBeanSource;
import com.kaba4cow.difuse.core.dependency.provider.DependencyProviderSession;
import com.kaba4cow.difuse.core.util.reflections.FieldScanner;

public abstract class FieldInjectBeanPostProcessor implements BeanPostProcessor {

	protected abstract Object getFieldValue(Field field, DependencyProviderSession session);

	protected abstract boolean filterField(Field field);

	@Override
	public final Object postProcess(Object bean, ClassBeanProvider beanProvider, DependencyProviderSession session) {
		ClassBeanSource beanSource = beanProvider.getBeanSource();
		findTargetFields(beanSource).forEach(field -> {
			try {
				field.setAccessible(true);
				field.set(bean, getFieldValue(field, session));
			} catch (Exception exception) {
				throw new BeanPostProcessorException(String.format("Could not initialize field %s for bean of type %s",
						field.getName(), beanSource.getBeanClass().getName()), exception);
			}
		});
		return bean;
	}

	protected final Set<Field> findTargetFields(ClassBeanSource beanSource) {
		return FieldScanner.of(beanSource.getBeanClass()).findFields().stream()//
				.filter(field -> !Modifier.isStatic(field.getModifiers()))//
				.filter(field -> !Modifier.isFinal(field.getModifiers()))//
				.filter(this::filterField)//
				.collect(Collectors.toSet());
	}

	@Override
	public final BeanLifecyclePhase getLifecyclePhase() {
		return BeanLifecyclePhase.FIELD_INJECT;
	}

}
