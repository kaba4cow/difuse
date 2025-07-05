package com.kaba4cow.difuse.core.bean.provider.support;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.system.SystemComponent;
import com.kaba4cow.difuse.core.annotation.system.SystemDependency;
import com.kaba4cow.difuse.core.bean.source.impl.ClassBeanSource;
import com.kaba4cow.difuse.core.bean.source.support.BeanSourceRegistry;
import com.kaba4cow.difuse.core.util.ExecutionTimer;

@SystemComponent
public class BeanProviderRegistrar {

	private static final Logger log = LoggerFactory.getLogger("BeanProviderRegistrar");

	@SystemDependency
	private BeanProviderFactory beanProviderFactory;

	@SystemDependency
	private BeanSourceRegistry beanSourceRegistry;

	public void registerBeanProviders() {
		log.info("Registering BeanProviders...");
		ExecutionTimer timer = new ExecutionTimer().start();

		Set<ClassBeanSource> classBeanSources = beanSourceRegistry.getBeanSources(ClassBeanSource.class);
		for (ClassBeanSource beanSource : classBeanSources)
			beanProviderFactory.createClassBeanProvider(beanSource);

		log.info("BeanProvider registration took {} ms", timer.finish().getExecutionMillis());
	}

}
