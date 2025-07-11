package com.kaba4cow.difuse.core.config.reader.support;

import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.config.reader.ConfigReader;
import com.kaba4cow.difuse.core.context.support.ContextRegistry;
import com.kaba4cow.difuse.core.context.support.ContextScanner;
import com.kaba4cow.difuse.core.util.LoggingTimer;

@SystemBean
public class ConfigReaderInitializer {

	private static final Logger log = LoggerFactory.getLogger("ConfigReaderInitializer");

	@Provided
	private ContextScanner contextScanner;

	@Provided
	private ContextRegistry contextRegistry;

	@Provided
	private ConfigReaderRegistrar configReaderRegistrar;

	public void initializeReaders() {
		try (LoggingTimer timer = new LoggingTimer(log, "Initializing ConfigReaders...")) {
			findReaderTypes().forEach(configReaderRegistrar::register);
		}
	}

	private Set<Class<? extends ConfigReader>> findReaderTypes() {
		return contextRegistry.getSourceClasses().stream()//
				.map(contextScanner::getScanner)//
				.map(packageScanner -> packageScanner.searchSubTypesOf(ConfigReader.class))//
				.flatMap(Set::stream)//
				.collect(Collectors.toSet());
	}

}
