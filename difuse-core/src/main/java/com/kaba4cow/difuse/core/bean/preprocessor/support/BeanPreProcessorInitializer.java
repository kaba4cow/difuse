package com.kaba4cow.difuse.core.bean.preprocessor.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.DifuseApplication;
import com.kaba4cow.difuse.core.annotation.system.SystemComponent;
import com.kaba4cow.difuse.core.annotation.system.SystemDependency;
import com.kaba4cow.difuse.core.context.source.ContextSource;
import com.kaba4cow.difuse.core.context.source.support.ContextSourceRegistry;
import com.kaba4cow.difuse.core.util.ExecutionTimer;

@SystemComponent
public class BeanPreProcessorInitializer {

	private static final Logger log = LoggerFactory.getLogger("BeanPreProcessorInitializer");

	@SystemDependency
	private ContextSourceRegistry contextSourceRegistry;

	@SystemDependency
	private BeanPreProcessorRegistrar beanPreProcessorRegistrar;

	public void initializeBeanPreProcessors() {
		log.info("Initializing BeanPreProcessors...");
		ExecutionTimer timer = new ExecutionTimer().start();

		beanPreProcessorRegistrar.registerBeanPreProcessors(DifuseApplication.class);
		for (ContextSource contextSource : contextSourceRegistry.getContextSources())
			beanPreProcessorRegistrar.registerBeanPreProcessors(contextSource.getSourceClass());

		log.info("BeanPreProcessor initialization took {} ms", timer.finish().getExecutionMillis());
	}

}
