package com.kaba4cow.difuse.core.context.source.configuration.builder;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import com.kaba4cow.difuse.core.annotation.context.WithConfigs;
import com.kaba4cow.difuse.core.annotation.context.WithContexts;
import com.kaba4cow.difuse.core.annotation.context.WithProfiles;
import com.kaba4cow.difuse.core.context.source.configuration.ContextSourceConfiguration;

public class SourceClassContextSourceConfigurationBuilder implements ContextSourceConfigurationBuilder {

	private final Class<?> sourceClass;

	public SourceClassContextSourceConfigurationBuilder(Class<?> sourceClass) {
		this.sourceClass = sourceClass;
	}

	@Override
	public ContextSourceConfiguration build() {
		DefaultContextSourceConfigurationBuilder builder = new DefaultContextSourceConfigurationBuilder();
		includeValueSet(WithConfigs.class, WithConfigs::value, builder::includeConfig);
		includeValueSet(WithProfiles.class, WithProfiles::value, builder::includeProfile);
		includeValueSet(WithContexts.class, WithContexts::value, builder::includeContext);
		return builder.build();
	}

	private <T, S extends Annotation> void includeValueSet(Class<S> annotation, Function<S, T[]> valueSetSupplier,
			Consumer<T> valueConsumer) {
		findAnnotation(annotation)//
				.map(valueSetSupplier)//
				.map(Arrays::asList)//
				.orElseGet(Collections::emptyList)//
				.forEach(valueConsumer);
	}

	private <T extends Annotation> Optional<T> findAnnotation(Class<T> annotation) {
		return Optional.ofNullable(sourceClass.getAnnotation(annotation));
	}

}
