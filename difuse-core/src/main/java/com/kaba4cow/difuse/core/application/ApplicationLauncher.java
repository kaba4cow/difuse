package com.kaba4cow.difuse.core.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.system.SystemDependency;
import com.kaba4cow.difuse.core.bean.BeanInitializer;
import com.kaba4cow.difuse.core.context.ContextInitializer;
import com.kaba4cow.difuse.core.dependency.provider.DependencyProvider;
import com.kaba4cow.difuse.core.dependency.provider.impl.DelegatingDependencyProvider;
import com.kaba4cow.difuse.core.environment.support.EnvironmentInitializer;
import com.kaba4cow.difuse.core.system.shutdownhook.SystemShutdownHookDispatcher;
import com.kaba4cow.difuse.core.util.ExecutionTimer;

public class ApplicationLauncher {

	private static final Logger log = LoggerFactory.getLogger("ApplicationLauncher");

	@SystemDependency
	private ContextInitializer contextInitializer;

	@SystemDependency
	private EnvironmentInitializer environmentInitializer;

	@SystemDependency
	private BeanInitializer beanInitializer;

	@SystemDependency
	private SystemShutdownHookDispatcher shutdownHookDispatcher;

	@SystemDependency
	private DelegatingDependencyProvider dependencyProvider;

	public DependencyProvider launch() {
		log.info("Launching application...");
		ExecutionTimer timer = new ExecutionTimer().start();

		contextInitializer.initializeContexts();
		environmentInitializer.initializeEnvironment();
		beanInitializer.initializeBeans();

		Runtime.getRuntime().addShutdownHook(shutdownHookDispatcher);

		log.info("Application launch took {} ms", timer.finish().getExecutionMillis());

		return dependencyProvider;
	}

}
