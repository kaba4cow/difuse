package com.kaba4cow.difuse.core.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.system.bean.SystemBeanInitializer;
import com.kaba4cow.difuse.core.system.bean.SystemBeanRegistrar;
import com.kaba4cow.difuse.core.system.bean.SystemBeanRegistry;
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

		SystemBeanRegistry componentRegistry = new SystemBeanRegistry();
		SystemBeanRegistrar componentRegistrar = new SystemBeanRegistrar(componentRegistry, shutdownHookRegistrar);

		SystemBeanInitializer componentInitializer = new SystemBeanInitializer(componentRegistry);

		componentRegistrar.registerBean(applicationParameters);
		componentRegistrar.registerBean(shutdownHookDispatcher);
		SystemLauncher launcher = componentRegistrar.registerBean(SystemLauncher.class);
		componentRegistrar.registerBeans();
		componentInitializer.initializeBeans();

		log.info("System initialization took {} ms", timer.finish().getExecutionMillis());

		return launcher;
	}

}
