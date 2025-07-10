package com.kaba4cow.difuse.core.bean.processor.pre.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.DifuseApplication;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.context.Context;
import com.kaba4cow.difuse.core.context.support.ContextRegistry;
import com.kaba4cow.difuse.core.util.LoggingTimer;

@SystemBean
public class BeanPreProcessorInitializer {

	private static final Logger log = LoggerFactory.getLogger("BeanPreProcessorInitializer");

	@Provided
	private ContextRegistry contextRegistry;

	@Provided
	private BeanPreProcessorRegistrar beanPreProcessorRegistrar;

	public void initializeBeanPreProcessors() {
		try (LoggingTimer timer = new LoggingTimer(log, "Initializing BeanPreProcessors...")) {
			beanPreProcessorRegistrar.registerBeanPreProcessors(DifuseApplication.class);
			for (Context context : contextRegistry.getContexts())
				beanPreProcessorRegistrar.registerBeanPreProcessors(context.getSourceClass());
		}
	}

}
