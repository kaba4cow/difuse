package com.kaba4cow.difuse.core.bean.destroyhook;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.bean.provider.BeanProvider;

@SystemBean
public class BeanDestroyHookRegistryPool {

	private final Map<BeanProvider<?>, BeanDestroyHookRegistry> pool = new ConcurrentHashMap<>();

	public BeanDestroyHookRegistry of(BeanProvider<?> beanProvider) {
		return pool.computeIfAbsent(beanProvider, key -> new BeanDestroyHookRegistry());
	}

	public static class BeanDestroyHookRegistry {

		private final Map<Object, List<BeanDestroyHook>> destroyHooks = new ConcurrentHashMap<>();

		private BeanDestroyHookRegistry() {}

		public void addDestroyHook(Object bean, BeanDestroyHook destroyHook) {
			getDestroyHooks(bean).add(destroyHook);
		}

		public List<BeanDestroyHook> getDestroyHooks(Object bean) {
			return destroyHooks.computeIfAbsent(bean, key -> new ArrayList<>());
		}

		public void clearDestroyHooks(Object bean) {
			if (destroyHooks.containsKey(bean)) {
				destroyHooks.get(bean).clear();
				destroyHooks.remove(bean);
			}
		}

	}

}
