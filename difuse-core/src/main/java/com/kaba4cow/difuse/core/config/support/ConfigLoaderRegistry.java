package com.kaba4cow.difuse.core.config.support;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.config.ConfigLoader;

@SystemBean
public class ConfigLoaderRegistry {

	private static final Logger log = LoggerFactory.getLogger("ConfigLoaderRegistry");

	private final Set<ConfigLoader> registry = ConcurrentHashMap.newKeySet();

	void registerLoader(ConfigLoader loader) {
		if (registry.add(loader))
			log.debug("Registered ConfigLoader {}", loader.getClass().getName());
	}

	public Set<ConfigLoader> getLoaders() {
		return Collections.unmodifiableSet(registry);
	}

}
