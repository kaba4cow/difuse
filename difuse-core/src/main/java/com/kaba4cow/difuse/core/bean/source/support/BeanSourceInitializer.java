package com.kaba4cow.difuse.core.bean.source.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.application.CoreComponent;
import com.kaba4cow.difuse.core.annotation.application.CoreDependency;
import com.kaba4cow.difuse.core.context.source.ContextSource;
import com.kaba4cow.difuse.core.context.source.support.ContextSourceRegistry;
import com.kaba4cow.difuse.core.util.ExecutionTimer;

@CoreComponent
public class BeanSourceInitializer {

	private static final Logger log = LoggerFactory.getLogger("BeanSourceInitializer");

	@CoreDependency
	private ContextSourceRegistry contextSourceRegistry;

	@CoreDependency
	private BeanSourceRegistrar beanSourceRegistrar;

	public void initializeBeanSources() {
		log.info("Initializing BeanSources...");
		ExecutionTimer timer = new ExecutionTimer().start();

		for (ContextSource contextSource : contextSourceRegistry.getContextSources())
			beanSourceRegistrar.registerBeanSources(contextSource);

		log.info("BeanSource initialization took {} ms", timer.finish().getExecutionMillis());
	}

}
