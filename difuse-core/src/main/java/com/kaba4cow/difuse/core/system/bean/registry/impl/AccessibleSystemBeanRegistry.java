package com.kaba4cow.difuse.core.system.bean.registry.impl;

import java.util.Optional;

import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.Accessible;
import com.kaba4cow.difuse.core.system.bean.registry.SystemBeanInstance;
import com.kaba4cow.difuse.core.system.bean.registry.SystemBeanRegistry;

@Accessible
public class AccessibleSystemBeanRegistry implements SystemBeanRegistry {

	@Provided
	private InternalSystemBeanRegistry registry;

	@Override
	public Optional<SystemBeanInstance> getInstance(Class<?> type) {
		return registry.getInstance(type)//
				.filter(SystemBeanInstance::isAccessible);
	}

	@Override
	public boolean containsBean(Class<?> type) {
		return registry.containsBean(type)//
				&& registry.getInstance(type)//
						.map(SystemBeanInstance::isAccessible)//
						.orElse(false);
	}

}
