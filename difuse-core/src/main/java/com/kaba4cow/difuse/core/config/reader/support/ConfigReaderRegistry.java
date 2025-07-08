package com.kaba4cow.difuse.core.config.reader.support;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.config.reader.ConfigReader;

@SystemBean
public class ConfigReaderRegistry {

	private static final Logger log = LoggerFactory.getLogger("ConfigReaderRegistry");

	private final Map<String, ConfigReader> registry = new ConcurrentHashMap<>();

	void registerReader(ConfigReader reader) {
		registry.put(reader.getExtension(), reader);
		log.debug("Registered '{}' ConfigReader {}", reader.getExtension(), reader.getClass().getName());
	}

	public Collection<ConfigReader> getReaders() {
		return Collections.unmodifiableCollection(registry.values());
	}

}
