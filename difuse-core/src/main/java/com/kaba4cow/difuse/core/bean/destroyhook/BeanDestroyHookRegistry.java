package com.kaba4cow.difuse.core.bean.destroyhook;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BeanDestroyHookRegistry {

	private final Map<Object, List<BeanDestroyHook>> registry = new ConcurrentHashMap<>();

	public void addDestroyHook(Object bean, BeanDestroyHook destroyHook) {
		registry.computeIfAbsent(bean, key -> new ArrayList<>()).add(destroyHook);
	}

	public void runDestroyHooks(Object bean) {
		if (registry.containsKey(bean)) {
			List<BeanDestroyHook> hooks = registry.get(bean);
			for (BeanDestroyHook hook : hooks)
				hook.onDestroy();
			registry.get(bean).clear();
		}
	}

}
