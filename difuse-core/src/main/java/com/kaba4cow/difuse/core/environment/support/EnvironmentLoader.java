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
import com.kaba4cow.difuse.core.environment.Environment;
import com.kaba4cow.difuse.core.environment.EnvironmentException;
import com.kaba4cow.difuse.core.environment.config.reader.ConfigSourceReader;
import com.kaba4cow.difuse.core.environment.config.reader.support.ConfigSourceReaderRegistry;
import com.kaba4cow.difuse.core.environment.config.source.ConfigSource;
import com.kaba4cow.difuse.core.util.LoggingTimer;

@SystemBean
public class EnvironmentLoader {

	private static final Logger log = LoggerFactory.getLogger("EnvironmentLoader");

	@Provided
	private ConfigSourceReaderRegistry configSourceReaderRegistry;

	@Provided
	private Environment environment;

	public void loadEnvironment() {
		try (LoggingTimer timer = new LoggingTimer(log, "Loading environment...")) {
			Collection<ConfigSourceReader> readers = configSourceReaderRegistry.getReaders();
			for (ConfigSourceReader reader : readers) {
				Set<String> locations = getConfigLocations(reader.getSuffix());
				for (String location : locations)
					loadConfig(location, reader);
			}
		}
	}

	private Set<String> getConfigLocations(String suffix) {
		Set<String> profiles = environment.getProfiles();
		Set<String> configs = environment.getConfigs();
		Set<String> locations = new HashSet<>();
		for (String config : configs) {
			locations.add(buildConfigLocation(config, suffix));
			for (String profile : profiles)
				locations.add(buildConfigLocation(config, profile, suffix));
		}
		return locations;
	}

	private String buildConfigLocation(String config, String suffix) {
		return String.format("%s.%s", config, suffix);
	}

	private String buildConfigLocation(String config, String profile, String suffix) {
		return String.format("%s-%s.%s", config, profile, suffix);
	}

	private void loadConfig(String location, ConfigSourceReader reader) {
		InputStream input = getClass().getClassLoader().getResourceAsStream(location);
		if (Objects.nonNull(input))
			try {
				ConfigSource source = reader.read(location, input);
				environment.addPropertySource(source);
				log.debug("Loaded config '{}'", location);
			} catch (Exception exception) {
				throw new EnvironmentException(String.format("Failed to load config '%s'", location), exception);
			}
	}

}
