package com.kaba4cow.difuse.core.context.source;

import com.kaba4cow.difuse.core.context.source.configuration.ContextConfiguration;

public class Context {

	private final Class<?> sourceClass;

	private final ContextConfiguration configuration;

	public Context(Class<?> sourceClass, ContextConfiguration configuration) {
		this.sourceClass = sourceClass;
		this.configuration = configuration;
	}

	public Class<?> getSourceClass() {
		return sourceClass;
	}

	public ContextConfiguration getConfiguration() {
		return configuration;
	}

	@Override
	public String toString() {
		return String.format("Context [sourceClass=%s, configuration=%s]", sourceClass, configuration);
	}

}
