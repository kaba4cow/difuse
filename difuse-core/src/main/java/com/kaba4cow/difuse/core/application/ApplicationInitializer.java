package com.kaba4cow.difuse.core.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.system.SystemComponentInitializer;
import com.kaba4cow.difuse.core.system.SystemComponentRegistrar;
import com.kaba4cow.difuse.core.system.SystemComponentRegistry;
import com.kaba4cow.difuse.core.system.shutdownhook.SystemShutdownHookDispatcher;
import com.kaba4cow.difuse.core.system.shutdownhook.SystemShutdownHookRegistrar;
import com.kaba4cow.difuse.core.system.shutdownhook.SystemShutdownHookRegistry;
import com.kaba4cow.difuse.core.util.ExecutionTimer;

public class ApplicationInitializer {

	private static final Logger log = LoggerFactory.getLogger("ApplicationInitializer");

	public ApplicationLauncher initialize(ApplicationParameters applicationParameters) {
		log.info("Initializing application...");
		ExecutionTimer timer = new ExecutionTimer().start();

		SystemShutdownHookRegistry shutdownHookRegistry = new SystemShutdownHookRegistry();
		SystemShutdownHookRegistrar shutdownHookRegistrar = new SystemShutdownHookRegistrar(shutdownHookRegistry);
		SystemShutdownHookDispatcher shutdownHookDispatcher = new SystemShutdownHookDispatcher(shutdownHookRegistry);

		SystemComponentRegistry componentRegistry = new SystemComponentRegistry();
		SystemComponentRegistrar componentRegistrar = new SystemComponentRegistrar(componentRegistry, shutdownHookRegistrar);

		SystemComponentInitializer componentInitializer = new SystemComponentInitializer(componentRegistry);

		componentRegistrar.registerComponent(applicationParameters);
		componentRegistrar.registerComponent(shutdownHookDispatcher);
		ApplicationLauncher launcher = componentRegistrar.registerComponent(ApplicationLauncher.class);
		componentRegistrar.registerComponents();
		componentInitializer.initializeComponents();

		log.info("Application initialization took {} ms", timer.finish().getExecutionMillis());

		return launcher;
	}

}
