package com.kaba4cow.difuse.core.typeconverter.support.converter;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.Collection;

import com.kaba4cow.difuse.core.typeconverter.TypeConverterException;
import com.kaba4cow.difuse.core.typeconverter.support.GlobalTypeConverter;

public class ArrayConverter {

	private final GlobalTypeConverter typeConverter;

	public ArrayConverter(GlobalTypeConverter typeConverter) {
		this.typeConverter = typeConverter;
	}

	public Object convert(Object raw, Class<?> componentType) {
		if (raw instanceof String) {
			String[] parts = ((String) raw).split(",");
			return convertArray(parts, componentType);
		} else if (raw instanceof Collection<?>) {
			Collection<?> collection = (Collection<?>) raw;
			return convertArray(collection.toArray(), componentType);
		} else if (raw.getClass().isArray()) {
			int length = Array.getLength(raw);
			Object result = Array.newInstance(componentType, length);
			for (int i = 0; i < length; i++) {
				Object element = Array.get(raw, i);
				Array.set(result, i, typeConverter.convert(element, componentType));
			}
			return result;
		}
		throw new TypeConverterException(String.format("Expected string, collection or array for %s[], got %s",
				componentType.getTypeName(), raw.getClass().getName()));
	}

	private Object convertArray(Object[] input, Type componentType) {
		Object array = Array.newInstance((Class<?>) componentType, input.length);
		for (int i = 0; i < input.length; i++)
			Array.set(array, i, typeConverter.convert(input[i], componentType));
		return array;
	}

}
