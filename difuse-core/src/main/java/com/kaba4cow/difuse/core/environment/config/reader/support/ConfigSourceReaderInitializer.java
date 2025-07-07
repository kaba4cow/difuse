package com.kaba4cow.difuse.core.environment.config.reader.support;

import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.context.ContextScanner;
import com.kaba4cow.difuse.core.context.source.support.ContextSourceRegistry;
import com.kaba4cow.difuse.core.environment.config.reader.ConfigSourceReader;
import com.kaba4cow.difuse.core.util.LoggingTimer;

@SystemBean
public class ConfigSourceReaderInitializer {

	private static final Logger log = LoggerFactory.getLogger("ConfigSourceReaderInitializer");

	@Provided
	private ContextScanner contextScanner;

	@Provided
	private ContextSourceRegistry contextSourceRegistry;

	@Provided
	private ConfigSourceReaderRegistrar configSourceReaderRegistrar;

	public void initializeReaders() {
		try (LoggingTimer timer = new LoggingTimer(log, "Initializing ConfigSourceReaders...")) {
			findReaderTypes().forEach(configSourceReaderRegistrar::register);
		}
	}

	private Set<Class<? extends ConfigSourceReader>> findReaderTypes() {
		return contextSourceRegistry.getSourceClasses().stream()//
				.map(contextScanner::getScanner)//
				.map(packageScanner -> packageScanner.searchSubTypesOf(ConfigSourceReader.class))//
				.flatMap(Set::stream)//
				.collect(Collectors.toSet());
	}

}
