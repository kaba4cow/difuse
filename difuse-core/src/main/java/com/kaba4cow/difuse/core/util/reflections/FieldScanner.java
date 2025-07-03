package com.kaba4cow.difuse.core.util.reflections;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class FieldScanner {

	private final Class<?> type;

	private FieldScanner(Class<?> type) {
		this.type = type;
	}

	public static FieldScanner of(Class<?> type) {
		return new FieldScanner(type);
	}

	public Set<Field> findFields() {
		Set<Field> fields = new HashSet<>();
		Class<?> currentClass = type;
		while (Objects.nonNull(currentClass) && currentClass != Object.class) {
			for (Field field : currentClass.getDeclaredFields())
				fields.add(field);
			currentClass = currentClass.getSuperclass();
		}
		return fields;
	}

}
