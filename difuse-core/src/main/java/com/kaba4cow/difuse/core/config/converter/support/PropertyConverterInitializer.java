package com.kaba4cow.difuse.core.config.converter.support;

import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.config.converter.PropertyConverter;
import com.kaba4cow.difuse.core.context.ContextScanner;
import com.kaba4cow.difuse.core.context.source.support.ContextSourceRegistry;
import com.kaba4cow.difuse.core.util.LoggingTimer;

@SystemBean
public class PropertyConverterInitializer {

	private static final Logger log = LoggerFactory.getLogger("PropertyConverterInitializer");

	@Provided
	private ContextScanner contextScanner;

	@Provided
	private ContextSourceRegistry contextSourceRegistry;

	@Provided
	private PropertyConverterRegistrar propertyConverterRegistrar;

	public void initializeConverters() {
		try (LoggingTimer timer = new LoggingTimer(log, "Initializing PropertyConverters...")) {
			findConverterTypes().forEach(propertyConverterRegistrar::register);
		}
	}

	@SuppressWarnings("unchecked")
	private Set<Class<? extends PropertyConverter<?>>> findConverterTypes() {
		return contextSourceRegistry.getSourceClasses().stream()//
				.map(contextScanner::getScanner)//
				.map(packageScanner -> packageScanner.searchSubTypesOf(PropertyConverter.class))//
				.flatMap(Set::stream)//
				.map(type -> (Class<? extends PropertyConverter<?>>) type)//
				.collect(Collectors.toSet());
	}

}
