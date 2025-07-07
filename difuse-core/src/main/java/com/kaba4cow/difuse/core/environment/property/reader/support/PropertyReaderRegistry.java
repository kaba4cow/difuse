package com.kaba4cow.difuse.core.environment.property.reader.support;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.environment.property.reader.PropertyReader;

@SystemBean
public class PropertyReaderRegistry {

	private static final Logger log = LoggerFactory.getLogger("PropertyReaderRegistry");

	private final Map<String, PropertyReader> registry = new ConcurrentHashMap<>();

	void registerReader(PropertyReader reader) {
		registry.put(reader.getSuffix(), reader);
		log.debug("Registered '{}' PropertyReader {}", reader.getSuffix(), reader.getClass().getName());
	}

	public Collection<PropertyReader> getReaders() {
		return Collections.unmodifiableCollection(registry.values());
	}

}
