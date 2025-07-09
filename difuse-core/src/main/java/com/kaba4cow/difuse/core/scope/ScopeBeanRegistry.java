package com.kaba4cow.difuse.core.scope;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.kaba4cow.difuse.core.bean.provider.BeanProvider;

public class ScopeBeanRegistry {

	private final Map<BeanProvider<?>, List<Object>> registry = new ConcurrentHashMap<>();

	public void registerBean(BeanProvider<?> beanProvider, Object bean) {
		registry.computeIfAbsent(beanProvider, key -> new ArrayList<>()).add(bean);
	}

	public Set<Entry<BeanProvider<?>, List<Object>>> getBeanEntries() {
		return registry.entrySet();
	}

	public void clear() {
		registry.values().forEach(List::clear);
		registry.clear();
	}

}
