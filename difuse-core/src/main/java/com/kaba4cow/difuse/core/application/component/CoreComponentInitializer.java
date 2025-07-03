package com.kaba4cow.difuse.core.application.component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import com.kaba4cow.difuse.core.DifuseException;
import com.kaba4cow.difuse.core.annotation.application.CoreDependency;

public class CoreComponentInitializer {

	private final CoreComponentRegistry coreComponentRegistry;

	public CoreComponentInitializer(CoreComponentRegistry coreComponentRegistry) {
		this.coreComponentRegistry = coreComponentRegistry;
	}

	public void initializeComponents() {
		Collection<Object> components = coreComponentRegistry.getAllComponents();
		for (Object component : components)
			initializeComponent(component);
	}

	private void initializeComponent(Object component) {
		Class<?> type = component.getClass();
		for (Field field : findDependencyFields(type))
			try {
				Object dependency = coreComponentRegistry.getComponent(field.getType());
				field.setAccessible(true);
				field.set(component, dependency);
			} catch (Exception exception) {
				throw new DifuseException(String.format("Could not provide dependency for CoreComponent of type %s", type),
						exception);
			}
	}

	private Set<Field> findDependencyFields(Class<?> type) {
		return Arrays.stream(type.getDeclaredFields())//
				.filter(field -> field.isAnnotationPresent(CoreDependency.class))//
				.collect(Collectors.toSet());
	}

}
