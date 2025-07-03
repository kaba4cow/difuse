package com.kaba4cow.difuse.core.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BeanDestroyHookRegistry {

	private final Map<Object, List<Runnable>> registry = new ConcurrentHashMap<>();

	public void registerDestroyHook(Object bean, Runnable hook) {
		registry.computeIfAbsent(bean, key -> new ArrayList<>()).add(hook);
	}

	public void runDestroyHooks(Object bean) {
		if (registry.containsKey(bean)) {
			List<Runnable> hooks = registry.get(bean);
			for (Runnable hook : hooks)
				hook.run();
			registry.get(bean).clear();
		}
		registry.clear();
	}

}
