package com.kaba4cow.difuse.core.system.bean;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.kaba4cow.difuse.core.annotation.system.Accessible;

public class SystemBeanRegistry {

	private final Map<Class<?>, Object> internal = new HashMap<>();

	private final Map<Class<?>, Object> accessible = new HashMap<>();

	public void registerBean(Object bean) {
		Class<?> type = bean.getClass();
		internal.put(type, bean);
		if (type.isAnnotationPresent(Accessible.class))
			accessible.put(type, bean);
	}

	public <T> T getInternalBean(Class<T> type) {
		return type.cast(internal.get(type));
	}

	public boolean containsInternalBean(Class<?> type) {
		return internal.containsKey(type);
	}

	public Collection<Object> getInternalBeans() {
		return Collections.unmodifiableCollection(internal.values());
	}

	public Collection<Object> getAccessibleBeans() {
		return Collections.unmodifiableCollection(accessible.values());
	}

	public <T> T getAccessibleBean(Class<T> type) {
		return type.cast(accessible.get(type));
	}

	public boolean containsAccessibleBean(Class<?> type) {
		return accessible.containsKey(type);
	}

}
