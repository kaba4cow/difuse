package com.kaba4cow.difuse.core.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.system.SystemComponent;
import com.kaba4cow.difuse.core.annotation.system.SystemDependency;
import com.kaba4cow.difuse.core.bean.postprocessor.support.BeanPostProcessorInitializer;
import com.kaba4cow.difuse.core.bean.preprocessor.support.BeanPreProcessorInitializer;
import com.kaba4cow.difuse.core.bean.provider.support.BeanProviderInitializer;
import com.kaba4cow.difuse.core.bean.source.support.BeanSourceInitializer;
import com.kaba4cow.difuse.core.bean.source.validator.support.BeanSourceValidatorInitializer;
import com.kaba4cow.difuse.core.util.ExecutionTimer;

@SystemComponent
public class BeanInitializer {

	private static final Logger log = LoggerFactory.getLogger("BeanInitializer");

	@SystemDependency
	private BeanSourceValidatorInitializer beanSourceValidatorInitializer;

	@SystemDependency
	private BeanPreProcessorInitializer beanPreProcessorInitializer;

	@SystemDependency
	private BeanPostProcessorInitializer beanPostProcessorInitializer;

	@SystemDependency
	private BeanSourceInitializer beanSourceInitializer;

	@SystemDependency
	private BeanProviderInitializer beanProviderInitializer;

	public void initializeBeans() {
		log.info("Initializing beans...");
		ExecutionTimer timer = new ExecutionTimer().start();

		beanSourceValidatorInitializer.initializeBeanSourceValidators();
		beanPreProcessorInitializer.initializeBeanPreProcessors();
		beanPostProcessorInitializer.initializeBeanPostProcessors();
		beanSourceInitializer.initializeBeanSources();
		beanProviderInitializer.initializeBeanProviders();

		log.info("Bean initialization took {} ms", timer.finish().getExecutionMillis());
	}

}
