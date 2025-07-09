package com.kaba4cow.difuse.core.type.descriptor;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class TypeDescriptor {

	private final Type type;

	private TypeDescriptor(Type type) {
		this.type = type;
	}

	public static TypeDescriptor of(Type type) {
		return new TypeDescriptor(type);
	}

	public Type getType() {
		return type;
	}

	public boolean isParameterized() {
		return type instanceof ParameterizedType;
	}

	public ParameterizedType getParameterizedType() {
		return (ParameterizedType) type;
	}

	public boolean isGenericArray() {
		return type instanceof GenericArrayType;
	}

	public GenericArrayType getGenericArrayType() {
		return (GenericArrayType) type;
	}

	public boolean isClass() {
		return type instanceof Class<?>;
	}

	public Class<?> getClassType() {
		return (Class<?>) type;
	}

	public boolean isEnum() {
		return isClass() && getClassType().isEnum();
	}

	public boolean isArray() {
		return isClass() && getClassType().isArray() || isGenericArray();
	}

	public boolean isInstance(Object obj) {
		return isClass() && getClassType().isInstance(obj);
	}

	public Class<?> getRawClass() {
		if (isClass())
			return (Class<?>) type;
		if (isParameterized())
			return (Class<?>) getParameterizedType().getRawType();
		if (isGenericArray()) {
			Type componentType = getGenericComponentType();
			if (componentType instanceof Class<?>)
				return Array.newInstance((Class<?>) componentType, 0).getClass();
		}
		throw new IllegalStateException(String.format("Could not determine raw class from type %s", type));
	}

	public Type getGenericComponentType() {
		return getGenericArrayType().getGenericComponentType();
	}

	public Type[] getGenericArguments() {
		if (isParameterized())
			return getParameterizedType().getActualTypeArguments();
		if (isGenericArray())
			return new Type[] { getGenericArrayType().getGenericComponentType() };
		if (isArray())
			return new Type[] { getClassType().getComponentType() };
		return new Type[0];
	}

	public Class<?> getComponentClass() {
		if (isGenericArray()) {
			Type componentType = getGenericComponentType();
			if (componentType instanceof Class<?>)
				return (Class<?>) getGenericComponentType();
		}
		if (isArray())
			return getClassType().getComponentType();
		throw new IllegalStateException(String.format("Not an array type: %s", type));
	}

}
