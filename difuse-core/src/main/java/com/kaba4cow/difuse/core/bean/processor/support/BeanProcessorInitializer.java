package com.kaba4cow.difuse.core.bean.processor.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.DifuseApplication;
import com.kaba4cow.difuse.core.annotation.application.CoreComponent;
import com.kaba4cow.difuse.core.annotation.application.CoreDependency;
import com.kaba4cow.difuse.core.context.source.ContextSource;
import com.kaba4cow.difuse.core.context.source.support.ContextSourceRegistry;
import com.kaba4cow.difuse.core.util.ExecutionTimer;

@CoreComponent
public class BeanProcessorInitializer {

	private static final Logger log = LoggerFactory.getLogger("BeanProcessorInitializer");

	@CoreDependency
	private ContextSourceRegistry contextSourceRegistry;

	@CoreDependency
	private BeanProcessorRegistrar beanProcessorRegistrar;

	public void initializeBeanProcessors() {
		log.info("Initializing BeanProcessors...");
		ExecutionTimer timer = new ExecutionTimer().start();

		beanProcessorRegistrar.registerBeanProcessors(DifuseApplication.class);
		for (ContextSource contextSource : contextSourceRegistry.getContextSources())
			beanProcessorRegistrar.registerBeanProcessors(contextSource.getSourceClass());

		log.info("BeanProcessor initialization took {} ms", timer.finish().getExecutionMillis());
	}

}
