package com.kaba4cow.difuse.core.bean.source.validator.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.DifuseApplication;
import com.kaba4cow.difuse.core.annotation.system.SystemComponent;
import com.kaba4cow.difuse.core.annotation.system.SystemDependency;
import com.kaba4cow.difuse.core.context.source.ContextSource;
import com.kaba4cow.difuse.core.context.source.support.ContextSourceRegistry;
import com.kaba4cow.difuse.core.util.ExecutionTimer;

@SystemComponent
public class BeanSourceValidatorInitializer {

	private static final Logger log = LoggerFactory.getLogger("BeanSourceValidatorInitializer");

	@SystemDependency
	private ContextSourceRegistry contextSourceRegistry;

	@SystemDependency
	private BeanSourceValidatorRegistrar beanSourceValidatorRegistrar;

	public void initializeBeanSourceValidators() {
		log.info("Initializing BeanSourceValidators...");
		ExecutionTimer timer = new ExecutionTimer().start();

		beanSourceValidatorRegistrar.registerBeanSourceValidators(DifuseApplication.class);
		for (ContextSource contextSource : contextSourceRegistry.getContextSources())
			beanSourceValidatorRegistrar.registerBeanSourceValidators(contextSource.getSourceClass());

		log.info("BeanSourceValidator initialization took {} ms", timer.finish().getExecutionMillis());
	}

}
