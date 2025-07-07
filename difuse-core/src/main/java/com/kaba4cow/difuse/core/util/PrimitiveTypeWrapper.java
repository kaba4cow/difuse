package com.kaba4cow.difuse.core.util;

import java.util.HashMap;
import java.util.Map;

public class PrimitiveTypeWrapper {

	private static final Map<Class<?>, Class<?>> wrappers = new HashMap<>();

	private PrimitiveTypeWrapper() {}

	static {
		wrappers.put(boolean.class, Boolean.class);
		wrappers.put(byte.class, Byte.class);
		wrappers.put(char.class, Character.class);
		wrappers.put(short.class, Short.class);
		wrappers.put(int.class, Integer.class);
		wrappers.put(long.class, Long.class);
		wrappers.put(float.class, Float.class);
		wrappers.put(double.class, Double.class);
	}

	public static Class<?> wrapIfPrimitive(Class<?> type) {
		return type.isPrimitive()//
				? wrappers.get(type)//
				: type;
	}

}
