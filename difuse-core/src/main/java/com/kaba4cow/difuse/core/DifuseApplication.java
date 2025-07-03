package com.kaba4cow.difuse.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.application.ApplicationInitializer;
import com.kaba4cow.difuse.core.application.ApplicationLauncher;
import com.kaba4cow.difuse.core.application.ApplicationParameters;
import com.kaba4cow.difuse.core.dependency.provider.DependencyProvider;
import com.kaba4cow.difuse.core.util.Assert;
import com.kaba4cow.difuse.core.util.ExecutionTimer;

public class DifuseApplication {

	private static final Logger log = LoggerFactory.getLogger("DifuseApplication");

	private final Class<?> sourceClass;

	private Class<?> testClass;

	public DifuseApplication(Class<?> sourceClass) {
		Assert.notNull(sourceClass, "Source class must not be null");
		this.sourceClass = sourceClass;
	}

	public DifuseApplication asTest(Class<?> testClass) {
		this.testClass = testClass;
		return this;
	}

	public DependencyProvider run(String[] args) {
		Assert.notNull(args, "Args must not be null");

		log.info("Starting application {}...", sourceClass.getName());
		ExecutionTimer timer = new ExecutionTimer().start();

		ApplicationParameters parameters = new ApplicationParameters(sourceClass, args, testClass);
		ApplicationLauncher launcher = new ApplicationInitializer().initialize(parameters);
		DependencyProvider dependencyProvider = launcher.launch();

		log.info("Application startup took {} ms", timer.finish().getExecutionMillis());

		return dependencyProvider;
	}

	public DependencyProvider run() {
		return run(new String[0]);
	}

}
