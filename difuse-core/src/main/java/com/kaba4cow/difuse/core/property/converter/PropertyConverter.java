package com.kaba4cow.difuse.core.property.converter;

public interface PropertyConverter<T> {

	Class<T> getTargetType();

	T convert(String raw);

}