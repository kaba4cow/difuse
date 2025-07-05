package com.kaba4cow.difuse.core.environment.support;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.annotation.system.SystemDependency;
import com.kaba4cow.difuse.core.environment.Environment;
import com.kaba4cow.difuse.core.environment.config.reader.ConfigSourceReader;
import com.kaba4cow.difuse.core.environment.config.reader.impl.PropertiesFileConfigSourceReader;
import com.kaba4cow.difuse.core.environment.config.source.ConfigSource;
import com.kaba4cow.difuse.core.util.ExecutionTimer;

@SystemBean
public class EnvironmentLoader {

	private static final Logger log = LoggerFactory.getLogger("EnvironmentLoader");

	private final Map<String, ConfigSourceReader> readers = new HashMap<>();

	@SystemDependency
	private Environment environment;

	public void loadEnvironment() {
		log.info("Loading environment...");
		ExecutionTimer timer = new ExecutionTimer().start();

		initializeReaders();

		for (Map.Entry<String, ConfigSourceReader> entry : readers.entrySet()) {
			String extension = entry.getKey();
			ConfigSourceReader reader = entry.getValue();
			tryLoadConfig(String.format("application.%s", extension), reader);
			for (String profile : environment.getProfiles())
				tryLoadConfig(String.format("application-%s.%s", profile, extension), reader);
		}

		log.info("Environment loading took {} ms", timer.finish().getExecutionMillis());
	}

	private void initializeReaders() {
		readers.put("properties", new PropertiesFileConfigSourceReader());
	}

	private void tryLoadConfig(String location, ConfigSourceReader reader) {
		InputStream input = getClass().getClassLoader().getResourceAsStream(location);
		if (Objects.nonNull(input))
			try {
				ConfigSource source = reader.read(location, input);
				environment.addPropertySource(source);
				log.debug("Loaded config '{}'", location);
			} catch (Exception exception) {
				log.error("Failed to load config '{}'", location);
			}
	}

}
