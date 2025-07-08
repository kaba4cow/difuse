package com.kaba4cow.difuse.core.environment.support;

import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.config.loader.ConfigLoader;
import com.kaba4cow.difuse.core.config.loader.support.ConfigLoaderRegistry;
import com.kaba4cow.difuse.core.config.reader.ConfigReader;
import com.kaba4cow.difuse.core.config.reader.support.ConfigReaderRegistry;
import com.kaba4cow.difuse.core.config.source.ConfigSource;
import com.kaba4cow.difuse.core.environment.Environment;
import com.kaba4cow.difuse.core.environment.EnvironmentException;
import com.kaba4cow.difuse.core.util.LoggingTimer;

@SystemBean
public class EnvironmentLoader {

	private static final Logger log = LoggerFactory.getLogger("EnvironmentLoader");

	@Provided
	private ConfigLoaderRegistry configLoaderRegistry;

	@Provided
	private ConfigReaderRegistry configReaderRegistry;

	@Provided
	private Environment environment;

	public void loadEnvironment() {
		try (LoggingTimer timer = new LoggingTimer(log, "Loading environment...")) {
			Collection<ConfigReader> readers = configReaderRegistry.getReaders();
			for (ConfigReader reader : readers) {
				Set<String> locations = getConfigLocations(reader.getExtension());
				for (String location : locations)
					loadConfig(location, reader);
			}
		}
	}

	private Set<String> getConfigLocations(String extension) {
		Set<String> profiles = environment.getProfiles();
		Set<String> configs = environment.getConfigs();
		Set<String> locations = new HashSet<>();
		for (String config : configs) {
			locations.add(buildConfigLocation(config, extension));
			for (String profile : profiles)
				locations.add(buildConfigLocation(config, profile, extension));
		}
		return locations;
	}

	private String buildConfigLocation(String config, String extension) {
		return String.format("%s.%s", config, extension);
	}

	private String buildConfigLocation(String config, String profile, String extension) {
		return String.format("%s-%s.%s", config, profile, extension);
	}

	private void loadConfig(String config, ConfigReader reader) {
		Set<ConfigLoader> loaders = configLoaderRegistry.getLoaders();
		for (ConfigLoader loader : loaders)
			loadConfig(config, reader, loader);
	}

	private void loadConfig(String config, ConfigReader reader, ConfigLoader loader) {
		InputStream input = loader.getInputStream(config);
		if (Objects.nonNull(input))
			try {
				ConfigSource source = reader.read(config, input);
				environment.addPropertySource(source);
				log.debug("Loaded config '{}' using {}", config, loader.getClass().getName());
			} catch (Exception exception) {
				throw new EnvironmentException(
						String.format("Failed to load config '%s' using {}", config, loader.getClass().getName()), exception);
			}
	}

}
