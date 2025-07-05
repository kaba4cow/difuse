package com.kaba4cow.difuse.core.system.component;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SystemComponentRegistry {

	private final Map<Class<?>, Object> registry = new HashMap<>();

	public void registerComponent(Object component) {
		registry.put(component.getClass(), component);
	}

	public <T> T getComponent(Class<T> type) {
		return type.cast(registry.get(type));
	}

	public Collection<Object> getAllComponents() {
		return Collections.unmodifiableCollection(registry.values());
	}

}
