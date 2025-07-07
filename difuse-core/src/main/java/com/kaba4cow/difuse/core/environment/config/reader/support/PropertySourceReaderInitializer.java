package com.kaba4cow.difuse.core.environment.config.reader.support;

import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.context.ContextScanner;
import com.kaba4cow.difuse.core.context.source.support.ContextSourceRegistry;
import com.kaba4cow.difuse.core.environment.config.reader.PropertySourceReader;
import com.kaba4cow.difuse.core.util.LoggingTimer;

@SystemBean
public class PropertySourceReaderInitializer {

	private static final Logger log = LoggerFactory.getLogger("PropertySourceReaderInitializer");

	@Provided
	private ContextScanner contextScanner;

	@Provided
	private ContextSourceRegistry contextSourceRegistry;

	@Provided
	private PropertySourceReaderRegistrar configSourceReaderRegistrar;

	public void initializeReaders() {
		try (LoggingTimer timer = new LoggingTimer(log, "Initializing PropertySourceReaders...")) {
			findReaderTypes().forEach(configSourceReaderRegistrar::register);
		}
	}

	private Set<Class<? extends PropertySourceReader>> findReaderTypes() {
		return contextSourceRegistry.getSourceClasses().stream()//
				.map(contextScanner::getScanner)//
				.map(packageScanner -> packageScanner.searchSubTypesOf(PropertySourceReader.class))//
				.flatMap(Set::stream)//
				.collect(Collectors.toSet());
	}

}
