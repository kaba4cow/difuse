package com.kaba4cow.difuse.core.scope.handler;

import java.util.List;
import java.util.Map;

import com.kaba4cow.difuse.core.bean.provider.BeanProvider;

public abstract class ScopeHandler {

	private final ScopeHandlerBeanRegistry beanRegistry = new ScopeHandlerBeanRegistry();

	public ScopeHandler() {}

	protected abstract Object requestBean(BeanProvider<?> beanProvider);

	protected abstract void cleanUp();

	public final Object getBean(BeanProvider<?> beanProvider) {
		Object bean = requestBean(beanProvider);
		beanRegistry.registerBean(beanProvider, bean);
		return bean;
	}

	public final long destroyBeans() {
		long total = 0L;
		for (Map.Entry<BeanProvider<?>, List<Object>> beanEntry : beanRegistry.getBeanEntries()) {
			BeanProvider<?> beanProvider = beanEntry.getKey();
			List<Object> beans = beanEntry.getValue();
			for (Object bean : beans) {
				beanProvider.destroyBean(bean);
				total++;
			}
		}
		cleanUp();
		beanRegistry.clear();
		return total;
	}

}
