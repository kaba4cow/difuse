package com.kaba4cow.difuse.core.system.bean;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SystemBeanRegistry {

	private final Map<Class<?>, Object> registry = new HashMap<>();

	public void registerBean(Object bean) {
		registry.put(bean.getClass(), bean);
	}

	public <T> T getBean(Class<T> type) {
		return type.cast(registry.get(type));
	}

	public boolean containsBean(Class<?> type) {
		return registry.containsKey(type);
	}

	public Collection<Object> getAllBeans() {
		return Collections.unmodifiableCollection(registry.values());
	}

}
