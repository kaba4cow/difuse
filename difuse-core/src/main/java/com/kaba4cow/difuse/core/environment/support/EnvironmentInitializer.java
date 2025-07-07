package com.kaba4cow.difuse.core.environment.support;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.context.source.configuration.ContextSourceConfiguration;
import com.kaba4cow.difuse.core.context.source.support.ContextSourceRegistry;
import com.kaba4cow.difuse.core.environment.Environment;
import com.kaba4cow.difuse.core.environment.config.reader.support.ConfigSourceReaderInitializer;
import com.kaba4cow.difuse.core.environment.config.source.impl.CliConfigSource;
import com.kaba4cow.difuse.core.environment.config.source.impl.EnvConfigSource;
import com.kaba4cow.difuse.core.system.SystemParameters;
import com.kaba4cow.difuse.core.util.LoggingTimer;

@SystemBean
public class EnvironmentInitializer {

	private static final Logger log = LoggerFactory.getLogger("EnvironmentInitializer");

	@Provided
	private ConfigSourceReaderInitializer configSourceReaderInitializer;

	@Provided
	private ContextSourceRegistry contextSourceRegistry;

	@Provided
	private Environment environment;

	@Provided
	private EnvironmentLoader environmentLoader;

	@Provided
	private SystemParameters systemParameters;

	public void initializeEnvironment() {
		try (LoggingTimer timer = new LoggingTimer(log, "Initializing environment...")) {
			String[] args = systemParameters.getCommandLineArgs();

			log.debug("Command line args: {}", Arrays.asList(args));
			log.debug("Active profiles: {}", environment.getProfiles());

			environment.addPropertySource(new CliConfigSource("cli", args));
			environment.addPropertySource(new EnvConfigSource("env"));

			configSourceReaderInitializer.initializeReaders();

			contextSourceRegistry.collectConfigurations(ContextSourceConfiguration::getIncludedProfiles)//
					.forEach(environment::includeProfile);

			contextSourceRegistry.collectConfigurations(ContextSourceConfiguration::getIncludedConfigs);
			environmentLoader.loadEnvironment();
		}
	}

}
