package com.kaba4cow.difuse.core.type.converter;

public interface PrimitiveTypeConverter<T> extends TypeConverter<T> {

	Class<?> getPrimitiveTargetType();

}
