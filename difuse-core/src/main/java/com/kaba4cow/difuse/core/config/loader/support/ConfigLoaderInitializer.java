package com.kaba4cow.difuse.core.config.loader.support;

import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.config.loader.ConfigLoader;
import com.kaba4cow.difuse.core.context.support.ContextRegistry;
import com.kaba4cow.difuse.core.context.support.ContextScanner;
import com.kaba4cow.difuse.core.util.LoggingTimer;

@SystemBean
public class ConfigLoaderInitializer {

	private static final Logger log = LoggerFactory.getLogger("ConfigLoaderInitializer");

	@Provided
	private ContextScanner contextScanner;

	@Provided
	private ContextRegistry contextRegistry;

	@Provided
	private ConfigLoaderRegistrar configLoaderRegistrar;

	public void initializeLoaders() {
		try (LoggingTimer timer = new LoggingTimer(log, "Initializing ConfigLoaders...")) {
			findLoaderTypes().forEach(configLoaderRegistrar::register);
		}
	}

	private Set<Class<? extends ConfigLoader>> findLoaderTypes() {
		return contextRegistry.getSourceClasses().stream()//
				.map(contextScanner::getScanner)//
				.map(packageScanner -> packageScanner.searchSubTypesOf(ConfigLoader.class))//
				.flatMap(Set::stream)//
				.collect(Collectors.toSet());
	}

}
