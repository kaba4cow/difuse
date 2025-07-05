package com.kaba4cow.difuse.core.bean.provider.support;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.annotation.system.SystemDependency;
import com.kaba4cow.difuse.core.bean.source.impl.ClassBeanSource;
import com.kaba4cow.difuse.core.bean.source.support.BeanSourceRegistry;
import com.kaba4cow.difuse.core.util.LoggingTimer;

@SystemBean
public class BeanProviderRegistrar {

	private static final Logger log = LoggerFactory.getLogger("BeanProviderRegistrar");

	@SystemDependency
	private BeanProviderFactory beanProviderFactory;

	@SystemDependency
	private BeanSourceRegistry beanSourceRegistry;

	public void registerBeanProviders() {
		try (LoggingTimer timer = new LoggingTimer(log, "Registering BeanProviders...")) {
			Set<ClassBeanSource> classBeanSources = beanSourceRegistry.getBeanSources(ClassBeanSource.class);
			for (ClassBeanSource beanSource : classBeanSources)
				beanProviderFactory.createClassBeanProvider(beanSource);
		}
	}

}
