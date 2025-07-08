package com.kaba4cow.difuse.core.environment.support;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.config.converter.support.TypeConverterInitializer;
import com.kaba4cow.difuse.core.config.loader.support.ConfigLoaderInitializer;
import com.kaba4cow.difuse.core.config.reader.support.ConfigReaderInitializer;
import com.kaba4cow.difuse.core.config.source.impl.CliConfigSource;
import com.kaba4cow.difuse.core.config.source.impl.EnvConfigSource;
import com.kaba4cow.difuse.core.context.source.configuration.ContextSourceConfiguration;
import com.kaba4cow.difuse.core.context.source.support.ContextSourceRegistry;
import com.kaba4cow.difuse.core.environment.Environment;
import com.kaba4cow.difuse.core.system.SystemParameters;
import com.kaba4cow.difuse.core.util.LoggingTimer;

@SystemBean
public class EnvironmentInitializer {

	private static final Logger log = LoggerFactory.getLogger("EnvironmentInitializer");

	@Provided
	private ConfigLoaderInitializer configLoaderInitializer;

	@Provided
	private ConfigReaderInitializer configReaderInitializer;

	@Provided
	private TypeConverterInitializer typeConverterInitializer;

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

			environment.addPropertySource(new CliConfigSource("cli", args));
			environment.addPropertySource(new EnvConfigSource("env"));

			configLoaderInitializer.initializeLoaders();
			configReaderInitializer.initializeReaders();
			typeConverterInitializer.initializeConverters();

			contextSourceRegistry//
					.collectConfigurations(ContextSourceConfiguration::getIncludedProfiles)//
					.forEach(environment::includeProfile);
			contextSourceRegistry//
					.collectConfigurations(ContextSourceConfiguration::getIncludedConfigs)//
					.forEach(environment::includeConfig);

			log.debug("Command line args: {}", Arrays.asList(args));
			log.debug("Active profiles: {}", environment.getProfiles());

			environmentLoader.loadEnvironment();
		}
	}

}
