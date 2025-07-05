package com.kaba4cow.difuse.core.context.source.support;

import com.kaba4cow.difuse.core.DifuseException;
import com.kaba4cow.difuse.core.annotation.context.DifuseContext;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.annotation.system.SystemDependency;
import com.kaba4cow.difuse.core.context.source.ContextSource;
import com.kaba4cow.difuse.core.context.source.ContextSourceBuilder;
import com.kaba4cow.difuse.core.context.source.configuration.ContextSourceConfiguration;
import com.kaba4cow.difuse.core.context.source.configuration.builder.SourceClassContextSourceConfigurationBuilder;

@SystemBean
public class ContextSourceFactory {

	@SystemDependency
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
