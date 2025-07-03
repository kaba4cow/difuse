package com.kaba4cow.difuse.core.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.application.CoreComponent;
import com.kaba4cow.difuse.core.annotation.application.CoreDependency;
import com.kaba4cow.difuse.core.bean.processor.support.BeanProcessorInitializer;
import com.kaba4cow.difuse.core.bean.provider.support.BeanProviderInitializer;
import com.kaba4cow.difuse.core.bean.source.support.BeanSourceInitializer;
import com.kaba4cow.difuse.core.bean.source.validator.support.BeanSourceValidatorInitializer;
import com.kaba4cow.difuse.core.util.ExecutionTimer;

@CoreComponent
public class BeanInitializer {

	private static final Logger log = LoggerFactory.getLogger("BeanInitializer");

	@CoreDependency
	private BeanSourceValidatorInitializer beanSourceValidatorInitializer;

	@CoreDependency
	private BeanProcessorInitializer beanProcessorInitializer;

	@CoreDependency
	private BeanSourceInitializer beanSourceInitializer;

	@CoreDependency
	private BeanProviderInitializer beanProviderInitializer;

	public void initializeBeans() {
		log.info("Initializing beans...");
		ExecutionTimer timer = new ExecutionTimer().start();

		beanSourceValidatorInitializer.initializeBeanSourceValidators();
		beanProcessorInitializer.initializeBeanProcessors();
		beanSourceInitializer.initializeBeanSources();
		beanProviderInitializer.initializeBeanProviders();

		log.info("Bean initialization took {} ms", timer.finish().getExecutionMillis());
	}

}
