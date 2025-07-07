package com.kaba4cow.difuse.core.environment.config.reader.support;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.environment.config.reader.PropertySourceReader;

@SystemBean
public class PropertySourceReaderRegistry {

	private static final Logger log = LoggerFactory.getLogger("PropertySourceReaderRegistry");

	private final Map<String, PropertySourceReader> registry = new ConcurrentHashMap<>();

	void registerReader(PropertySourceReader reader) {
		registry.put(reader.getSuffix(), reader);
		log.debug("Registered '{}' PropertySourceReader {}", reader.getSuffix(), reader.getClass().getName());
	}

	public Collection<PropertySourceReader> getReaders() {
		return Collections.unmodifiableCollection(registry.values());
	}

}
