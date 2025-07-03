package com.kaba4cow.difuse.scheduling.failbehavior;

import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.kaba4cow.difuse.scheduling.annotation.CancelOnFail;

public class FailBehaviorFactory {

	private FailBehaviorFactory() {}

	public static FailBehavior createFailBehavior(AnnotatedElement element) {
		Optional<Set<Class<? extends Throwable>>> throwables = getThrowables(element.getAnnotation(CancelOnFail.class));
		return new FailBehavior(//
				throwables.isPresent(), //
				throwables.orElseGet(Collections::emptySet)//
		);
	}

	private static Optional<Set<Class<? extends Throwable>>> getThrowables(CancelOnFail annotation) {
		return Objects.isNull(annotation)//
				? Optional.empty()//
				: Optional.of(Arrays.stream(annotation.value()).collect(Collectors.toSet()));
	}

}
