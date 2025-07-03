package com.kaba4cow.difuse.core.test;

import com.kaba4cow.difuse.core.context.source.ContextSource;
import com.kaba4cow.difuse.core.context.source.ContextSourceBuilder;
import com.kaba4cow.difuse.core.context.source.configuration.builder.SourceClassContextSourceConfigurationBuilder;
import com.kaba4cow.difuse.core.dependency.DependencyConsumer;

public class TestDependencyConsumer implements DependencyConsumer {

	private final ContextSource contextSource;

	public TestDependencyConsumer(Class<?> testClass) {
		this.contextSource = new ContextSourceBuilder()//
				.sourceClass(testClass)//
				.configuration(new SourceClassContextSourceConfigurationBuilder(testClass).build())//
				.build();
	}

	@Override
	public ContextSource getContextSource() {
		return contextSource;
	}

	@Override
	public Class<?> getConsumerClass() {
		return contextSource.getSourceClass();
	}

}
