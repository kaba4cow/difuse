package com.kaba4cow.difuse.core.util;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

public class MethodSignature {

	private final String name;

	private final Class<?>[] parameters;

	private MethodSignature(String name, Class<?>[] parameters) {
		this.name = name;
		this.parameters = parameters;
	}

	public static MethodSignature of(Method method) {
		return new MethodSignature(method.getName(), method.getParameterTypes());
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, Arrays.hashCode(parameters));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MethodSignature other = (MethodSignature) obj;
		return Objects.equals(name, other.name) && Arrays.equals(parameters, other.parameters);
	}

	@Override
	public String toString() {
		return String.format("MethodSignature [name=%s, parameters=%s]", name, Arrays.toString(parameters));
	}

}