package com.kaba4cow.difuse.core.type;

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

	public boolean isType(Type type) {
		return this.type == type;
	}

	public TypeDescriptor getRawType() {
		if (isClass())
			return of(type);
		if (isParameterized())
			return of(getParameterizedType().getRawType());
		if (isGenericArray()) {
			TypeDescriptor component = getGenericComponentType();
			if (component.isClass())
				return of(Array.newInstance(component.getClassType(), 0).getClass());
		}
		throw new IllegalStateException(String.format("Could not determine raw class from type %s", type));
	}

	public TypeDescriptor getGenericComponentType() {
		return of(getGenericArrayType().getGenericComponentType());
	}

	public TypeDescriptor[] getGenericArguments() {
		if (isParameterized()) {
			Type[] types = getParameterizedType().getActualTypeArguments();
			TypeDescriptor[] descriptors = new TypeDescriptor[types.length];
			for (int i = 0; i < types.length; i++)
				descriptors[i] = of(types[i]);
			return descriptors;
		}
		if (isGenericArray())
			return new TypeDescriptor[] { getGenericComponentType() };
		if (isArray())
			return new TypeDescriptor[] { of(getClassType().getComponentType()) };
		return new TypeDescriptor[0];
	}

	public TypeDescriptor getComponentType() {
		if (isGenericArray()) {
			TypeDescriptor component = getGenericComponentType();
			if (component.isClass())
				return of(Array.newInstance(component.getClassType(), 0).getClass());
		}
		if (isArray())
			return of(getClassType().getComponentType());
		throw new IllegalStateException(String.format("Not an array type: %s", type));
	}

	@Override
	public String toString() {
		return type.getTypeName();
	}

}
