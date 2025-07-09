package com.kaba4cow.difuse.core.config.reader.support;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.config.reader.ConfigReader;

@SystemBean
public class ConfigReaderRegistry {

	private static final Logger log = LoggerFactory.getLogger("ConfigReaderRegistry");

	private final Set<ConfigReader> registry = ConcurrentHashMap.newKeySet();

	void registerReader(ConfigReader reader) {
		if (registry.add(reader))
			log.debug("Registered ConfigReader {} for extensions {}", reader.getClass().getName(), reader.getExtensions());
	}

	public Set<ConfigReader> getReaders() {
		return Collections.unmodifiableSet(registry);
	}

}
