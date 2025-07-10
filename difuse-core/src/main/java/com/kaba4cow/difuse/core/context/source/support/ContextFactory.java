package com.kaba4cow.difuse.core.context.source.support;

import com.kaba4cow.difuse.core.DifuseException;
import com.kaba4cow.difuse.core.annotation.context.DifuseContext;
import com.kaba4cow.difuse.core.context.source.Context;
import com.kaba4cow.difuse.core.context.source.ContextBuilder;
import com.kaba4cow.difuse.core.context.source.configuration.ContextConfiguration;
import com.kaba4cow.difuse.core.context.source.configuration.builder.SourceClassContextConfigurationBuilder;

public class ContextFactory {

	private final ContextRegistry contextRegistry;

	public ContextFactory(ContextRegistry contextRegistry) {
		this.contextRegistry = contextRegistry;
	}

	public void createContext(Class<?> sourceClass) {
		if (!sourceClass.isAnnotationPresent(DifuseContext.class))
			throw new DifuseException(String.format("Class %s is not a DifuseContext", sourceClass.getName()));
		if (contextRegistry.contains(sourceClass))
			return;
		ContextConfiguration configuration = new SourceClassContextConfigurationBuilder(sourceClass).build();
		Context context = new ContextBuilder()//
				.sourceClass(sourceClass)//
				.configuration(configuration)//
				.build();
		contextRegistry.register(sourceClass, context);
		configuration.getIncludedContexts().forEach(this::createContext);
	}

}
