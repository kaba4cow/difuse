package com.kaba4cow.difuse.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.dependency.provider.DependencyProvider;
import com.kaba4cow.difuse.core.system.SystemInitializer;
import com.kaba4cow.difuse.core.system.SystemLauncher;
import com.kaba4cow.difuse.core.system.SystemParameters;
import com.kaba4cow.difuse.core.util.Assert;
import com.kaba4cow.difuse.core.util.LoggingTimer;

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
		try (LoggingTimer timer = new LoggingTimer(log, "Starting application {}...", sourceClass.getName())) {
			Assert.notNull(args, "Args must not be null");
			SystemParameters parameters = new SystemParameters(sourceClass, args, testClass);
			SystemInitializer initializer = new SystemInitializer();
			SystemLauncher launcher = initializer.initialize(parameters);
			DependencyProvider dependencyProvider = launcher.launch();
			return dependencyProvider;
		}
	}

	public DependencyProvider run() {
		return run(new String[0]);
	}

}
