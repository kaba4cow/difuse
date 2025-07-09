package com.kaba4cow.difuse.core.type.converter;

public interface TypeConverter<T> {

	Class<T> getTargetType();

	T convert(String raw);

}