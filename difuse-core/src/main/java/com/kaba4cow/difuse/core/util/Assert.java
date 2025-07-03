package com.kaba4cow.difuse.core.util;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class Assert {

	private Assert() {}

	public static void fail(String message) {
		throw new IllegalStateException(message);
	}

	public static void isTrue(boolean value, String message) {
		if (value != true)
			fail(message);
	}

	public static void isFalse(boolean value, String message) {
		if (value != false)
			fail(message);
	}

	public static <T> T isNull(T object, String message) {
		if (Objects.nonNull(object))
			fail(message);
		return object;
	}

	public static <T> T notNull(T object, String message) {
		if (Objects.isNull(object))
			fail(message);
		return object;
	}

	public static void equal(Object a, Object b, String message) {
		if (!Objects.equals(a, b))
			fail(message);
	}

	public static void notEqual(Object a, Object b, String message) {
		if (Objects.equals(a, b))
			fail(message);
	}

	public static void deepEqual(Object a, Object b, String message) {
		if (!Objects.deepEquals(a, b))
			fail(message);
	}

	public static void notDeepEqual(Object a, Object b, String message) {
		if (Objects.deepEquals(a, b))
			fail(message);
	}

	public static <T extends Collection<?>> T isEmpty(T collection, String message) {
		if (!collection.isEmpty())
			fail(message);
		return collection;
	}

	public static <T extends Collection<?>> T notEmpty(T collection, String message) {
		if (collection.isEmpty())
			fail(message);
		return collection;
	}

	public static <T extends Map<?, ?>> T isEmpty(T map, String message) {
		if (!map.isEmpty())
			fail(message);
		return map;
	}

	public static <T extends Map<?, ?>> T notEmpty(T map, String message) {
		if (map.isEmpty())
			fail(message);
		return map;
	}

}
