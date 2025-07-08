package com.kaba4cow.difuse.core.typeconverter;

public interface TypeConverter<T> {

	Class<T> getTargetType();

	T convert(String raw);

}