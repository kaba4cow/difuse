package com.kaba4cow.difuse.core.context.configuration.builder;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.kaba4cow.difuse.core.annotation.context.DifuseContext;
import com.kaba4cow.difuse.core.annotation.context.WithConfigs;
import com.kaba4cow.difuse.core.annotation.context.WithContexts;
import com.kaba4cow.difuse.core.annotation.context.WithProfiles;
import com.kaba4cow.difuse.core.context.configuration.ContextConfiguration;

public class SourceClassContextConfigurationBuilder implements ContextConfigurationBuilder {

	private final Class<?> sourceClass;

	public SourceClassContextConfigurationBuilder(Class<?> sourceClass) {
		this.sourceClass = sourceClass;
	}

	@Override
	public ContextConfiguration build() {
		DefaultContextConfigurationBuilder builder = new DefaultContextConfigurationBuilder();
		includeValueSet(WithConfigs.class, WithConfigs::value, builder::includeConfig);
		includeValueSet(WithProfiles.class, WithProfiles::value, builder::includeProfile);
		includeValueSet(WithContexts.class, WithContexts::value, builder::includeContext);
		findContextAnnotations().forEach(builder::includeContext);
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

	private Set<Class<? extends Annotation>> findContextAnnotations() {
		return Arrays.stream(sourceClass.getAnnotations())//
				.map(Annotation::annotationType)//
				.filter(annotation -> annotation.isAnnotationPresent(DifuseContext.class))//
				.collect(Collectors.toSet());
	}

}
