package com.kaba4cow.difuse.core.environment.support;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.config.support.ConfigLoaderInitializer;
import com.kaba4cow.difuse.core.context.source.configuration.ContextSourceConfiguration;
import com.kaba4cow.difuse.core.context.source.support.ContextSourceRegistry;
import com.kaba4cow.difuse.core.environment.Environment;
import com.kaba4cow.difuse.core.property.converter.support.PropertyConverterInitializer;
import com.kaba4cow.difuse.core.property.reader.support.PropertyReaderInitializer;
import com.kaba4cow.difuse.core.property.source.impl.CliPropertySource;
import com.kaba4cow.difuse.core.property.source.impl.EnvPropertySource;
import com.kaba4cow.difuse.core.system.SystemParameters;
import com.kaba4cow.difuse.core.util.LoggingTimer;

@SystemBean
public class EnvironmentInitializer {

	private static final Logger log = LoggerFactory.getLogger("EnvironmentInitializer");

	@Provided
	private ConfigLoaderInitializer configLoaderInitializer;

	@Provided
	private PropertyReaderInitializer propertyReaderInitializer;

	@Provided
	private PropertyConverterInitializer propertyConverterInitializer;

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

			environment.addPropertySource(new CliPropertySource("cli", args));
			environment.addPropertySource(new EnvPropertySource("env"));

			configLoaderInitializer.initializeLoaders();
			propertyReaderInitializer.initializeReaders();
			propertyConverterInitializer.initializeConverters();

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
