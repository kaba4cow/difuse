package com.kaba4cow.difuse.core.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.context.source.support.ContextSourceFactory;
import com.kaba4cow.difuse.core.context.source.support.ContextSourceRegistry;
import com.kaba4cow.difuse.core.system.SystemParameters;
import com.kaba4cow.difuse.core.util.LoggingTimer;

public class ContextInitializer {

	private static final Logger log = LoggerFactory.getLogger("ContextInitializer");

	private final SystemParameters systemParameters;

	public ContextInitializer(SystemParameters systemParameters) {
		this.systemParameters = systemParameters;
	}

	public ContextSourceRegistry initializeContexts() {
		try (LoggingTimer timer = new LoggingTimer(log, "Initializing contexts...")) {
			ContextSourceRegistry contextSourceRegistry = new ContextSourceRegistry();
			ContextSourceFactory contextSourceFactory = new ContextSourceFactory(contextSourceRegistry);
			contextSourceFactory.createContextSource(systemParameters.getSourceClass());
			return contextSourceRegistry;
		}
	}

}
