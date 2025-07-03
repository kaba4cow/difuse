package com.kaba4cow.difuse.core.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.application.CoreComponent;
import com.kaba4cow.difuse.core.annotation.application.CoreDependency;
import com.kaba4cow.difuse.core.application.ApplicationParameters;
import com.kaba4cow.difuse.core.context.source.support.ContextSourceFactory;
import com.kaba4cow.difuse.core.context.source.support.ContextSourceRegistry;
import com.kaba4cow.difuse.core.util.ExecutionTimer;

@CoreComponent
public class ContextInitializer {

	private static final Logger log = LoggerFactory.getLogger("ContextInitializer");

	@CoreDependency
	private ContextSourceRegistry contextSourceRegistry;

	@CoreDependency
	private ContextSourceFactory contextSourceFactory;

	@CoreDependency
	private ApplicationParameters bootstrapStartupParameters;

	public void initializeContexts() {
		log.info("Initializing contexts...");
		ExecutionTimer timer = new ExecutionTimer().start();

		contextSourceFactory.createContextSource(bootstrapStartupParameters.getSourceClass());

		log.info("Context initialization took {} ms", timer.finish().getExecutionMillis());
	}

}
