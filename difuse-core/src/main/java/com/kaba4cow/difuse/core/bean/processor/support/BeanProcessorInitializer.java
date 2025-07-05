package com.kaba4cow.difuse.core.bean.processor.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.DifuseApplication;
import com.kaba4cow.difuse.core.annotation.system.SystemComponent;
import com.kaba4cow.difuse.core.annotation.system.SystemDependency;
import com.kaba4cow.difuse.core.context.source.ContextSource;
import com.kaba4cow.difuse.core.context.source.support.ContextSourceRegistry;
import com.kaba4cow.difuse.core.util.ExecutionTimer;

@SystemComponent
public class BeanProcessorInitializer {

	private static final Logger log = LoggerFactory.getLogger("BeanProcessorInitializer");

	@SystemDependency
	private ContextSourceRegistry contextSourceRegistry;

	@SystemDependency
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
