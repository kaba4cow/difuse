package com.kaba4cow.difuse.core.system.bean;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import com.kaba4cow.difuse.core.DifuseException;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.system.bean.registry.impl.InternalSystemBeanRegistry;

public class SystemBeanInjector {

	private final InternalSystemBeanRegistry beanRegistry;

	public SystemBeanInjector(InternalSystemBeanRegistry beanRegistry) {
		this.beanRegistry = beanRegistry;
	}

	public void injectDependencies(Object bean) {
		Class<?> type = bean.getClass();
		for (Field field : findDependencyFields(type))
			try {
				Object dependency = beanRegistry.getBean(field.getType());
				field.setAccessible(true);
				field.set(bean, dependency);
			} catch (Exception exception) {
				throw new DifuseException(
						String.format("Could not provide dependency %s for %s", field.getType().getName(), type.getName()),
						exception);
			}
	}

	private Set<Field> findDependencyFields(Class<?> type) {
		return Arrays.stream(type.getDeclaredFields())//
				.filter(field -> field.isAnnotationPresent(Provided.class))//
				.collect(Collectors.toSet());
	}

}
