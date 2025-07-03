package com.kaba4cow.difuse.core.context.source;

import java.util.Objects;

import com.kaba4cow.difuse.core.context.source.configuration.ContextSourceConfiguration;

public class ContextSourceBuilder {

	private Class<?> sourceClass;

	private ContextSourceConfiguration configuration;

	public ContextSourceBuilder() {}

	public ContextSourceBuilder sourceClass(Class<?> sourceClass) {
		this.sourceClass = sourceClass;
		return this;
	}

	public ContextSourceBuilder configuration(ContextSourceConfiguration configuration) {
		this.configuration = configuration;
		return this;
	}

	public ContextSource build() {
		Objects.requireNonNull(sourceClass);
		Objects.requireNonNull(configuration);
		return new ContextSource(sourceClass, configuration);
	}

}
