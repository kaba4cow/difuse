package com.kaba4cow.difuse.core.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.system.component.SystemComponentInitializer;
import com.kaba4cow.difuse.core.system.component.SystemComponentRegistrar;
import com.kaba4cow.difuse.core.system.component.SystemComponentRegistry;
import com.kaba4cow.difuse.core.system.shutdownhook.SystemShutdownHookDispatcher;
import com.kaba4cow.difuse.core.system.shutdownhook.SystemShutdownHookRegistrar;
import com.kaba4cow.difuse.core.system.shutdownhook.SystemShutdownHookRegistry;
import com.kaba4cow.difuse.core.util.ExecutionTimer;

public class SystemInitializer {

	private static final Logger log = LoggerFactory.getLogger("SystemInitializer");

	public SystemLauncher initialize(SystemParameters applicationParameters) {
		log.info("Initializing system...");
		ExecutionTimer timer = new ExecutionTimer().start();

		SystemShutdownHookRegistry shutdownHookRegistry = new SystemShutdownHookRegistry();
		SystemShutdownHookRegistrar shutdownHookRegistrar = new SystemShutdownHookRegistrar(shutdownHookRegistry);
		SystemShutdownHookDispatcher shutdownHookDispatcher = new SystemShutdownHookDispatcher(shutdownHookRegistry);

		SystemComponentRegistry componentRegistry = new SystemComponentRegistry();
		SystemComponentRegistrar componentRegistrar = new SystemComponentRegistrar(componentRegistry, shutdownHookRegistrar);

		SystemComponentInitializer componentInitializer = new SystemComponentInitializer(componentRegistry);

		componentRegistrar.registerComponent(applicationParameters);
		componentRegistrar.registerComponent(shutdownHookDispatcher);
		SystemLauncher launcher = componentRegistrar.registerComponent(SystemLauncher.class);
		componentRegistrar.registerComponents();
		componentInitializer.initializeComponents();

		log.info("System initialization took {} ms", timer.finish().getExecutionMillis());

		return launcher;
	}

}
