package com.kaba4cow.difuse.core.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.context.source.support.ContextFactory;
import com.kaba4cow.difuse.core.context.source.support.ContextRegistry;
import com.kaba4cow.difuse.core.system.SystemParameters;
import com.kaba4cow.difuse.core.util.LoggingTimer;

public class ContextInitializer {

	private static final Logger log = LoggerFactory.getLogger("ContextInitializer");

	private final SystemParameters systemParameters;

	public ContextInitializer(SystemParameters systemParameters) {
		this.systemParameters = systemParameters;
	}

	public ContextRegistry initializeContexts() {
		try (LoggingTimer timer = new LoggingTimer(log, "Initializing contexts...")) {
			ContextRegistry contextRegistry = new ContextRegistry();
			ContextFactory contextFactory = new ContextFactory(contextRegistry);
			contextFactory.createContext(systemParameters.getSourceClass());
			return contextRegistry;
		}
	}

}
