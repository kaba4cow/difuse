package com.kaba4cow.difuse.core.context.source;

import com.kaba4cow.difuse.core.context.source.configuration.ContextSourceConfiguration;

public class ContextSource {

	private final Class<?> sourceClass;

	private final ContextSourceConfiguration configuration;

	public ContextSource(Class<?> sourceClass, ContextSourceConfiguration configuration) {
		this.sourceClass = sourceClass;
		this.configuration = configuration;
	}

	public Class<?> getSourceClass() {
		return sourceClass;
	}

	public ContextSourceConfiguration getConfiguration() {
		return configuration;
	}

	@Override
	public String toString() {
		return String.format("ContextSource [sourceClass=%s, configuration=%s]", sourceClass, configuration);
	}

}
