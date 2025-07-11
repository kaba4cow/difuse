package com.kaba4cow.difuse.core.scope;

import java.util.List;
import java.util.Map.Entry;

import com.kaba4cow.difuse.core.bean.destroyhook.BeanDestroyHookRunner;
import com.kaba4cow.difuse.core.bean.provider.BeanProvider;

public abstract class Scope {

	private final ScopeBeanRegistry beanRegistry = new ScopeBeanRegistry();

	protected abstract Object requestBean(BeanProvider<?> beanProvider);

	protected abstract void cleanUp();

	public final Object getBean(BeanProvider<?> beanProvider) {
		Object bean = requestBean(beanProvider);
		beanRegistry.registerBean(beanProvider, bean);
		return bean;
	}

	public final long destroyBeans(BeanDestroyHookRunner destroyHookRunner) {
		long total = 0L;
		for (Entry<BeanProvider<?>, List<Object>> beanEntry : beanRegistry.getBeanEntries()) {
			BeanProvider<?> beanProvider = beanEntry.getKey();
			List<Object> beans = beanEntry.getValue();
			for (Object bean : beans) {
				destroyHookRunner.runDestroyHooks(beanProvider, bean);
				total++;
			}
		}
		cleanUp();
		beanRegistry.clear();
		return total;
	}

}
