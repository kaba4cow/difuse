package com.kaba4cow.difuse.core.system.bean.registry;

import com.kaba4cow.difuse.core.annotation.system.Accessible;

public class SystemBeanInstance {

	private final Class<?> type;

	private final Object instance;

	private final boolean accessible;

	private SystemBeanInstance(Class<?> type, Object instance, boolean accessible) {
		this.type = type;
		this.instance = instance;
		this.accessible = accessible;
	}

	public static SystemBeanInstance of(Object bean) {
		Class<?> type = bean.getClass();
		boolean accessible = type.isAnnotationPresent(Accessible.class);
		return new SystemBeanInstance(type, bean, accessible);
	}

	public Class<?> getType() {
		return type;
	}

	public Object getInstance() {
		return instance;
	}

	public boolean isAccessible() {
		return accessible;
	}

	@Override
	public String toString() {
		return String.format("SystemBeanInstance [type=%s, accessible=%s]", type, accessible);
	}

}
