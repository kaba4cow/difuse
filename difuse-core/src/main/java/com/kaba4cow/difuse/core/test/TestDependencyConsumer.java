package com.kaba4cow.difuse.core.test;

import com.kaba4cow.difuse.core.context.source.Context;
import com.kaba4cow.difuse.core.context.source.ContextBuilder;
import com.kaba4cow.difuse.core.context.source.configuration.builder.SourceClassContextConfigurationBuilder;
import com.kaba4cow.difuse.core.dependency.DependencyConsumer;

public class TestDependencyConsumer implements DependencyConsumer {

	private final Context context;

	public TestDependencyConsumer(Class<?> testClass) {
		this.context = new ContextBuilder()//
				.sourceClass(testClass)//
				.configuration(new SourceClassContextConfigurationBuilder(testClass).build())//
				.build();
	}

	@Override
	public Context getContext() {
		return context;
	}

	@Override
	public Class<?> getConsumerClass() {
		return context.getSourceClass();
	}

}
