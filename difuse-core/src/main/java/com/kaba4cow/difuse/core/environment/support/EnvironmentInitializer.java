package com.kaba4cow.difuse.core.environment.support;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.system.SystemComponent;
import com.kaba4cow.difuse.core.annotation.system.SystemDependency;
import com.kaba4cow.difuse.core.context.source.configuration.ContextSourceConfiguration;
import com.kaba4cow.difuse.core.context.source.support.ContextSourceRegistry;
import com.kaba4cow.difuse.core.environment.Environment;
import com.kaba4cow.difuse.core.environment.config.source.impl.CliConfigSource;
import com.kaba4cow.difuse.core.environment.config.source.impl.EnvConfigSource;
import com.kaba4cow.difuse.core.system.SystemParameters;
import com.kaba4cow.difuse.core.util.ExecutionTimer;

@SystemComponent
public class EnvironmentInitializer {

	private static final Logger log = LoggerFactory.getLogger("EnvironmentInitializer");

	@SystemDependency
	private ContextSourceRegistry contextSourceRegistry;

	@SystemDependency
	private Environment environment;

	@SystemDependency
	private EnvironmentLoader environmentLoader;

	@SystemDependency
	private SystemParameters bootstrapStartupParameters;

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
