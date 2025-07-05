package com.kaba4cow.difuse.core.bean.postprocessor.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.DifuseApplication;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.annotation.system.SystemDependency;
import com.kaba4cow.difuse.core.context.source.ContextSource;
import com.kaba4cow.difuse.core.context.source.support.ContextSourceRegistry;
import com.kaba4cow.difuse.core.util.ExecutionTimer;

@SystemBean
public class BeanPostProcessorInitializer {

	private static final Logger log = LoggerFactory.getLogger("BeanPostProcessorInitializer");

	@SystemDependency
	private ContextSourceRegistry contextSourceRegistry;

	@SystemDependency
	private BeanPostProcessorRegistrar beanPostProcessorRegistrar;

	public void initializeBeanPostProcessors() {
		log.info("Initializing BeanPostProcessors...");
		ExecutionTimer timer = new ExecutionTimer().start();

		beanPostProcessorRegistrar.registerBeanPostProcessors(DifuseApplication.class);
		for (ContextSource contextSource : contextSourceRegistry.getContextSources())
			beanPostProcessorRegistrar.registerBeanPostProcessors(contextSource.getSourceClass());

		log.info("BeanPostProcessor initialization took {} ms", timer.finish().getExecutionMillis());
	}

}
