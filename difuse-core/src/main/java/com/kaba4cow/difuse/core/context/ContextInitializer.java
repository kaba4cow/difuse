package com.kaba4cow.difuse.core.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.annotation.system.SystemDependency;
import com.kaba4cow.difuse.core.context.source.support.ContextSourceFactory;
import com.kaba4cow.difuse.core.context.source.support.ContextSourceRegistry;
import com.kaba4cow.difuse.core.system.SystemParameters;
import com.kaba4cow.difuse.core.util.ExecutionTimer;

@SystemBean
public class ContextInitializer {

	private static final Logger log = LoggerFactory.getLogger("ContextInitializer");

	@SystemDependency
	private ContextSourceRegistry contextSourceRegistry;

	@SystemDependency
	private ContextSourceFactory contextSourceFactory;

	@SystemDependency
	private SystemParameters systemParameters;

	public void initializeContexts() {
		log.info("Initializing contexts...");
		ExecutionTimer timer = new ExecutionTimer().start();

		contextSourceFactory.createContextSource(systemParameters.getSourceClass());

		log.info("Context initialization took {} ms", timer.finish().getExecutionMillis());
	}

}
