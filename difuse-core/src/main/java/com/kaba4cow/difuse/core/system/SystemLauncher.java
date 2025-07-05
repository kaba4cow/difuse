package com.kaba4cow.difuse.core.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.bean.support.BeanInitializer;
import com.kaba4cow.difuse.core.context.ContextInitializer;
import com.kaba4cow.difuse.core.dependency.provider.DependencyProvider;
import com.kaba4cow.difuse.core.dependency.provider.impl.GlobalDependencyProvider;
import com.kaba4cow.difuse.core.environment.support.EnvironmentInitializer;
import com.kaba4cow.difuse.core.system.shutdownhook.SystemShutdownHookDispatcher;
import com.kaba4cow.difuse.core.util.LoggingTimer;

public class SystemLauncher {

	private static final Logger log = LoggerFactory.getLogger("SystemLauncher");

	@Provided
	private ContextInitializer contextInitializer;

	@Provided
	private EnvironmentInitializer environmentInitializer;

	@Provided
	private BeanInitializer beanInitializer;

	@Provided
	private SystemShutdownHookDispatcher shutdownHookDispatcher;

	@Provided
	private GlobalDependencyProvider dependencyProvider;

	public DependencyProvider launch() {
		try (LoggingTimer timer = new LoggingTimer(log, "Launching system...")) {
			contextInitializer.initializeContexts();
			environmentInitializer.initializeEnvironment();
			beanInitializer.initializeBeans();
			Runtime.getRuntime().addShutdownHook(shutdownHookDispatcher);
			return dependencyProvider;
		}
	}

}
