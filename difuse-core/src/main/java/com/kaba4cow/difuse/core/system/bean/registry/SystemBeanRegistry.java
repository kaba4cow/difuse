package com.kaba4cow.difuse.core.system.bean.registry;

import java.util.Objects;
import java.util.Optional;

public interface SystemBeanRegistry {

	default <T> T getBean(Class<T> type) {
		return getInstance(type)//
				.filter(Objects::nonNull)//
				.map(SystemBeanInstance::getInstance)//
				.map(type::cast)//
				.orElse(null);
	}

	Optional<SystemBeanInstance> getInstance(Class<?> type);

	boolean containsBean(Class<?> type);

}
