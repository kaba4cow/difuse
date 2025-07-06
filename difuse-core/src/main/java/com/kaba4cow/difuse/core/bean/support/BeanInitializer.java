package com.kaba4cow.difuse.core.bean.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.bean.processor.post.support.BeanPostProcessorInitializer;
import com.kaba4cow.difuse.core.bean.processor.pre.support.BeanPreProcessorInitializer;
import com.kaba4cow.difuse.core.bean.provider.support.BeanProviderInitializer;
import com.kaba4cow.difuse.core.bean.source.support.BeanSourceInitializer;
import com.kaba4cow.difuse.core.bean.source.validator.support.BeanSourceValidatorInitializer;
import com.kaba4cow.difuse.core.util.LoggingTimer;

@SystemBean
public class BeanInitializer {

	private static final Logger log = LoggerFactory.getLogger("BeanInitializer");

	@Provided
	private BeanSourceValidatorInitializer beanSourceValidatorInitializer;

	@Provided
	private BeanPreProcessorInitializer beanPreProcessorInitializer;

	@Provided
	private BeanPostProcessorInitializer beanPostProcessorInitializer;

	@Provided
	private BeanSourceInitializer beanSourceInitializer;

	@Provided
	private BeanProviderInitializer beanProviderInitializer;

	public void initializeBeans() {
		try (LoggingTimer timer = new LoggingTimer(log, "Initializing beans...")) {
			beanSourceValidatorInitializer.initializeBeanSourceValidators();
			beanPreProcessorInitializer.initializeBeanPreProcessors();
			beanPostProcessorInitializer.initializeBeanPostProcessors();
			beanSourceInitializer.initializeBeanSources();
			beanProviderInitializer.initializeBeanProviders();
		}
	}

}
