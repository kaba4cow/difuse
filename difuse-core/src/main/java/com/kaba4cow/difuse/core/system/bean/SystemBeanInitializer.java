package com.kaba4cow.difuse.core.system.bean;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import com.kaba4cow.difuse.core.DifuseException;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;

public class SystemBeanInitializer {

	private final SystemBeanRegistry beanRegistry;

	public SystemBeanInitializer(SystemBeanRegistry beanRegistry) {
		this.beanRegistry = beanRegistry;
	}

	public void initializeBeans() {
		Collection<Object> components = beanRegistry.getInternalBeans();
		for (Object component : components)
			initializeBean(component);
	}

	private void initializeBean(Object component) {
		Class<?> type = component.getClass();
		for (Field field : findDependencyFields(type))
			try {
				Object dependency = beanRegistry.getInternalBean(field.getType());
				field.setAccessible(true);
				field.set(component, dependency);
			} catch (Exception exception) {
				throw new DifuseException(String.format("Could not provide dependency for SystemBean %s", type), exception);
			}
	}

	private Set<Field> findDependencyFields(Class<?> type) {
		return Arrays.stream(type.getDeclaredFields())//
				.filter(field -> field.isAnnotationPresent(Provided.class))//
				.collect(Collectors.toSet());
	}

}
