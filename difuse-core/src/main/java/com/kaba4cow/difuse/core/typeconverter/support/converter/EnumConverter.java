package com.kaba4cow.difuse.core.typeconverter.support.converter;

import com.kaba4cow.difuse.core.typeconverter.TypeConverterException;

public class EnumConverter {

	@SuppressWarnings("unchecked")
	public <T extends Enum<T>> T convert(String raw, Class<?> type) {
		try {
			return Enum.valueOf((Class<T>) type, raw.trim().toUpperCase());
		} catch (Exception exception) {
			throw new TypeConverterException(String.format("Invalid value '%s' for enum %s", raw, type.getName()), exception);
		}
	}

}
