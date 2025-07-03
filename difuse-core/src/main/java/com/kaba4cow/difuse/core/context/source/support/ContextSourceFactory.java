package com.kaba4cow.difuse.core.context.source.support;

import com.kaba4cow.difuse.core.DifuseException;
import com.kaba4cow.difuse.core.annotation.application.CoreComponent;
import com.kaba4cow.difuse.core.annotation.application.CoreDependency;
import com.kaba4cow.difuse.core.annotation.context.DifuseContext;
import com.kaba4cow.difuse.core.context.source.ContextSource;
import com.kaba4cow.difuse.core.context.source.ContextSourceBuilder;
import com.kaba4cow.difuse.core.context.source.configuration.ContextSourceConfiguration;
import com.kaba4cow.difuse.core.context.source.configuration.builder.SourceClassContextSourceConfigurationBuilder;

@CoreComponent
public class ContextSourceFactory {

	@CoreDependency
	private ContextSourceRegistry contextSourceRegistry;

	public void createContextSource(Class<?> sourceClass) {
		if (!sourceClass.isAnnotationPresent(DifuseContext.class))
			throw new DifuseException(String.format("Class %s is not a DifuseContext", sourceClass.getName()));
		if (contextSourceRegistry.contains(sourceClass))
			return;
		ContextSourceConfiguration configuration = new SourceClassContextSourceConfigurationBuilder(sourceClass).build();
		ContextSource contextSource = new ContextSourceBuilder()//
				.sourceClass(sourceClass)//
				.configuration(configuration)//
				.build();
		contextSourceRegistry.register(sourceClass, contextSource);
		configuration.getIncludedContexts().forEach(this::createContextSource);
	}

}
