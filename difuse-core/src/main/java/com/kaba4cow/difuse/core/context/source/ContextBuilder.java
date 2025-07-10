package com.kaba4cow.difuse.core.context.source;

import java.util.Objects;

import com.kaba4cow.difuse.core.context.source.configuration.ContextConfiguration;

public class ContextBuilder {

	private Class<?> sourceClass;

	private ContextConfiguration configuration;

	public ContextBuilder sourceClass(Class<?> sourceClass) {
		this.sourceClass = sourceClass;
		return this;
	}

	public ContextBuilder configuration(ContextConfiguration configuration) {
		this.configuration = configuration;
		return this;
	}

	public Context build() {
		Objects.requireNonNull(sourceClass);
		Objects.requireNonNull(configuration);
		return new Context(sourceClass, configuration);
	}

}
