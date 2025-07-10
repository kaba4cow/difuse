package com.kaba4cow.difuse.core.type.converter.support;

import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.context.support.ContextRegistry;
import com.kaba4cow.difuse.core.context.support.ContextScanner;
import com.kaba4cow.difuse.core.type.converter.TypeConverter;
import com.kaba4cow.difuse.core.util.LoggingTimer;

@SystemBean
public class TypeConverterInitializer {

	private static final Logger log = LoggerFactory.getLogger("TypeConverterInitializer");

	@Provided
	private ContextScanner contextScanner;

	@Provided
	private ContextRegistry contextRegistry;

	@Provided
	private TypeConverterRegistrar typeConverterRegistrar;

	public void initializeConverters() {
		try (LoggingTimer timer = new LoggingTimer(log, "Initializing TypeConverters...")) {
			findConverterTypes().forEach(typeConverterRegistrar::register);
		}
	}

	@SuppressWarnings("unchecked")
	private Set<Class<? extends TypeConverter<?>>> findConverterTypes() {
		return contextRegistry.getSourceClasses().stream()//
				.map(contextScanner::getScanner)//
				.map(packageScanner -> packageScanner.searchSubTypesOf(TypeConverter.class))//
				.flatMap(Set::stream)//
				.map(type -> (Class<? extends TypeConverter<?>>) type)//
				.collect(Collectors.toSet());
	}

}
