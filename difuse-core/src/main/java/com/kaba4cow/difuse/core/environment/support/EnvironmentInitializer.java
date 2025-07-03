package com.kaba4cow.difuse.core.environment.support;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.application.CoreComponent;
import com.kaba4cow.difuse.core.annotation.application.CoreDependency;
import com.kaba4cow.difuse.core.application.ApplicationParameters;
import com.kaba4cow.difuse.core.context.source.configuration.ContextSourceConfiguration;
import com.kaba4cow.difuse.core.context.source.support.ContextSourceRegistry;
import com.kaba4cow.difuse.core.environment.Environment;
import com.kaba4cow.difuse.core.environment.config.source.impl.CliConfigSource;
import com.kaba4cow.difuse.core.environment.config.source.impl.EnvConfigSource;
import com.kaba4cow.difuse.core.util.ExecutionTimer;

@CoreComponent
public class EnvironmentInitializer {

	private static final Logger log = LoggerFactory.getLogger("EnvironmentInitializer");

	@CoreDependency
	private ContextSourceRegistry contextSourceRegistry;

	@CoreDependency
	private Environment environment;

	@CoreDependency
	private EnvironmentLoader environmentLoader;

	@CoreDependency
	private ApplicationParameters bootstrapStartupParameters;

	public void initializeEnvironment() {
		log.info("Initializing environment...");
		ExecutionTimer timer = new ExecutionTimer().start();

		String[] args = bootstrapStartupParameters.getCommandLineArgs();

		log.debug("Command line args: {}", Arrays.asList(args));
		log.debug("Active profiles: {}", environment.getProfiles());

		environment.addPropertySource(new CliConfigSource("cli", args));
		environment.addPropertySource(new EnvConfigSource("env"));

		contextSourceRegistry.collectConfigurations(ContextSourceConfiguration::getIncludedProfiles)//
				.forEach(environment::includeProfile);

		contextSourceRegistry.collectConfigurations(ContextSourceConfiguration::getIncludedConfigs);
		environmentLoader.loadEnvironment();

		log.info("Environment initialization took {} ms", timer.finish().getExecutionMillis());
	}

}
