package com.kaba4cow.difuse.core;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TestUtil {

	private TestUtil() {}

	public static List<Class<?>> mapObjectsToTypes(Collection<?> collection) {
		return collection.stream()//
				.map(Object::getClass)//
				.collect(Collectors.toList());
	}

}
