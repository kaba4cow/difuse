package com.kaba4cow.difuse.core.config.converter;

public interface TypeConverter<T> {

	Class<T> getTargetType();

	T convert(String raw);

}