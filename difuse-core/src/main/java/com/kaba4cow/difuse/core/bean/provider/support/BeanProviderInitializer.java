package com.kaba4cow.difuse.core.bean.provider.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.annotation.system.SystemDependency;
import com.kaba4cow.difuse.core.bean.provider.BeanProvider;
import com.kaba4cow.difuse.core.bean.provider.impl.ClassBeanProvider;
import com.kaba4cow.difuse.core.bean.provider.impl.MethodBeanProvider;
import com.kaba4cow.difuse.core.util.ExecutionTimer;

@SystemBean
public class BeanProviderInitializer {

	private static final Logger log = LoggerFactory.getLogger("BeanProviderInitializer");

	@SystemDependency
	private BeanProviderRegistry beanProviderRegistry;

	@SystemDependency
	private BeanProviderRegistrar beanProviderRegistrar;

	public void initializeBeanProviders() {
		log.info("Initializing BeanProviders...");
		ExecutionTimer timer = new ExecutionTimer().start();

		beanProviderRegistrar.registerBeanProviders();
		beanProviderRegistry.findEagerBeanProviders(MethodBeanProvider.class)//
				.forEach(BeanProvider::provideBean);
		beanProviderRegistry.findEagerBeanProviders(ClassBeanProvider.class)//
				.forEach(BeanProvider::provideBean);

		log.info("BeanProvider initialization took {} ms", timer.finish().getExecutionMillis());
	}

}
