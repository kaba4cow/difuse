package com.kaba4cow.difuse.core.property.reader.support;

import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.context.ContextScanner;
import com.kaba4cow.difuse.core.context.source.support.ContextSourceRegistry;
import com.kaba4cow.difuse.core.property.reader.PropertyReader;
import com.kaba4cow.difuse.core.util.LoggingTimer;

@SystemBean
public class PropertyReaderInitializer {

	private static final Logger log = LoggerFactory.getLogger("PropertyReaderInitializer");

	@Provided
	private ContextScanner contextScanner;

	@Provided
	private ContextSourceRegistry contextSourceRegistry;

	@Provided
	private PropertyReaderRegistrar propertyReaderRegistrar;

	public void initializeReaders() {
		try (LoggingTimer timer = new LoggingTimer(log, "Initializing PropertyReaders...")) {
			findReaderTypes().forEach(propertyReaderRegistrar::register);
		}
	}

	private Set<Class<? extends PropertyReader>> findReaderTypes() {
		return contextSourceRegistry.getSourceClasses().stream()//
				.map(contextScanner::getScanner)//
				.map(packageScanner -> packageScanner.searchSubTypesOf(PropertyReader.class))//
				.flatMap(Set::stream)//
				.collect(Collectors.toSet());
	}

}
