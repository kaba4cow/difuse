package com.kaba4cow.difuse.core.system.component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import com.kaba4cow.difuse.core.DifuseException;
import com.kaba4cow.difuse.core.annotation.system.SystemDependency;

public class SystemComponentInitializer {

	private final SystemComponentRegistry componentRegistry;

	public SystemComponentInitializer(SystemComponentRegistry componentRegistry) {
		this.componentRegistry = componentRegistry;
	}

	public void initializeComponents() {
		Collection<Object> components = componentRegistry.getAllComponents();
		for (Object component : components)
			initializeComponent(component);
	}

	private void initializeComponent(Object component) {
		Class<?> type = component.getClass();
		for (Field field : findDependencyFields(type))
			try {
				Object dependency = componentRegistry.getComponent(field.getType());
				field.setAccessible(true);
				field.set(component, dependency);
			} catch (Exception exception) {
				throw new DifuseException(String.format("Could not provide dependency for SystemComponent %s", type),
						exception);
			}
	}

	private Set<Field> findDependencyFields(Class<?> type) {
		return Arrays.stream(type.getDeclaredFields())//
				.filter(field -> field.isAnnotationPresent(SystemDependency.class))//
				.collect(Collectors.toSet());
	}

}
