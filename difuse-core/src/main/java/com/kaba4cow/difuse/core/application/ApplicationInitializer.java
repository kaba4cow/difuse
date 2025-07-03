package com.kaba4cow.difuse.core.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.application.component.CoreComponentInitializer;
import com.kaba4cow.difuse.core.application.component.CoreComponentRegistrar;
import com.kaba4cow.difuse.core.application.component.CoreComponentRegistry;
import com.kaba4cow.difuse.core.application.shutdownhook.ApplicationShutdownHookDispatcher;
import com.kaba4cow.difuse.core.application.shutdownhook.ApplicationShutdownHookRegistrar;
import com.kaba4cow.difuse.core.application.shutdownhook.ApplicationShutdownHookRegistry;
import com.kaba4cow.difuse.core.util.ExecutionTimer;

public class ApplicationInitializer {

	private static final Logger log = LoggerFactory.getLogger("ApplicationInitializer");

	public ApplicationInitializer() {}

	public ApplicationLauncher initialize(ApplicationParameters applicationParameters) {
		log.info("Initializing application...");
		ExecutionTimer timer = new ExecutionTimer().start();

		ApplicationShutdownHookRegistry shutdownHookRegistry = new ApplicationShutdownHookRegistry();
		ApplicationShutdownHookRegistrar shutdownHookRegistrar = new ApplicationShutdownHookRegistrar(shutdownHookRegistry);
		ApplicationShutdownHookDispatcher shutdownHookDispatcher = new ApplicationShutdownHookDispatcher(shutdownHookRegistry);

		CoreComponentRegistry componentRegistry = new CoreComponentRegistry();
		CoreComponentRegistrar componentRegistrar = new CoreComponentRegistrar(componentRegistry, shutdownHookRegistrar);

		CoreComponentInitializer componentInitializer = new CoreComponentInitializer(componentRegistry);

		componentRegistrar.registerComponent(applicationParameters);
		componentRegistrar.registerComponent(shutdownHookDispatcher);
		ApplicationLauncher launcher = componentRegistrar.registerComponent(ApplicationLauncher.class);
		componentRegistrar.registerComponents();
		componentInitializer.initializeComponents();

		log.info("Application initialization took {} ms", timer.finish().getExecutionMillis());

		return launcher;
	}

}
