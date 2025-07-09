package com.kaba4cow.difuse.core.bean.destroyhook;

import java.util.List;

import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.bean.destroyhook.BeanDestroyHookRegistryPool.BeanDestroyHookRegistry;
import com.kaba4cow.difuse.core.bean.provider.BeanProvider;

@SystemBean
public class BeanDestroyHookRunner {

	@Provided
	private BeanDestroyHookRegistryPool registryPool;

	public void runDestroyHooks(BeanProvider<?> beanProvider, Object bean) {
		BeanDestroyHookRegistry registry = registryPool.of(beanProvider);
		List<BeanDestroyHook> destroyHooks = registry.getDestroyHooks(bean);
		for (BeanDestroyHook destroyHook : destroyHooks)
			destroyHook.onDestroy();
		registry.clearDestroyHooks(bean);
	}

}
