package com.kaba4cow.difuse.core.system.bean.registry.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.kaba4cow.difuse.core.system.bean.registry.SystemBeanInstance;
import com.kaba4cow.difuse.core.system.bean.registry.SystemBeanRegistry;

public class InternalSystemBeanRegistry implements SystemBeanRegistry {

	private final Map<Class<?>, SystemBeanInstance> registry = new HashMap<>();

	public void registerBean(Object bean) {
		registry.put(bean.getClass(), SystemBeanInstance.of(bean));
	}

	@Override
	public Optional<SystemBeanInstance> getInstance(Class<?> type) {
		return Optional.ofNullable(registry.get(type));
	}

	@Override
	public boolean containsBean(Class<?> type) {
		return registry.containsKey(type);
	}

	public Set<Object> getAllBeans() {
		return registry.values().stream()//
				.map(SystemBeanInstance::getInstance)//
				.collect(Collectors.toSet());
	}

}
