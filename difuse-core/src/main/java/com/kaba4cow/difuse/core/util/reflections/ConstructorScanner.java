package com.kaba4cow.difuse.core.util.reflections;

import java.lang.reflect.Constructor;
import java.util.Arrays;

public class ConstructorScanner<T> {

	private final Class<T> type;

	private ConstructorScanner(Class<T> type) {
		this.type = type;
	}

	public static <T> ConstructorScanner<T> of(Class<T> type) {
		return new ConstructorScanner<>(type);
	}

	@SuppressWarnings("unchecked")
	public Constructor<T> findNoArgsConstructor() {
		try {
			Constructor<T>[] constructors = (Constructor<T>[]) type.getConstructors();
			if (constructors.length == 0)
				throw new RuntimeException("No public constructors found");
			else if (constructors.length == 1)
				return constructors[0];
			else
				return Arrays.stream(constructors)//
						.filter(constructor -> constructor.getParameterCount() == 0)//
						.findFirst()//
						.orElseThrow(() -> new RuntimeException("No empty constructor found"));
		} catch (Exception exception) {
			throw new RuntimeException("No target constructor found", exception);
		}
	}

}
