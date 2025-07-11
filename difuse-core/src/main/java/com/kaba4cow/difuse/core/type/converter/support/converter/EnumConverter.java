package com.kaba4cow.difuse.core.type.converter.support.converter;

import com.kaba4cow.difuse.core.type.TypeDescriptor;
import com.kaba4cow.difuse.core.type.converter.TypeConverterException;

public class EnumConverter {

	@SuppressWarnings("unchecked")
	public <T extends Enum<T>> T convert(String raw, TypeDescriptor type) {
		try {
			return Enum.valueOf((Class<T>) type.getClassType(), raw.trim().toUpperCase());
		} catch (Exception exception) {
			throw new TypeConverterException(String.format("Invalid value '%s' for enum %s", raw, type), exception);
		}
	}

}
