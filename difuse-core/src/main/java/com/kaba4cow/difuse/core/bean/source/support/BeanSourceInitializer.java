package com.kaba4cow.difuse.core.bean.source.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.annotation.system.SystemDependency;
import com.kaba4cow.difuse.core.context.source.ContextSource;
import com.kaba4cow.difuse.core.context.source.support.ContextSourceRegistry;
import com.kaba4cow.difuse.core.util.LoggingTimer;

@SystemBean
public class BeanSourceInitializer {

	private static final Logger log = LoggerFactory.getLogger("BeanSourceInitializer");

	@SystemDependency
	private ContextSourceRegistry contextSourceRegistry;

	@SystemDependency
	private BeanSourceRegistrar beanSourceRegistrar;

	public void initializeBeanSources() {
		try (LoggingTimer timer = new LoggingTimer(log, "Initializing BeanSources...")) {
			for (ContextSource contextSource : contextSourceRegistry.getContextSources())
				beanSourceRegistrar.registerBeanSources(contextSource);
		}
	}

}
