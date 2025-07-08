package com.kaba4cow.difuse.core.config.converter.support.converter;

import com.kaba4cow.difuse.core.config.converter.PropertyConverterException;

public class EnumConverter {

	@SuppressWarnings("unchecked")
	public <T extends Enum<T>> T convert(String raw, Class<?> type) {
		try {
			return Enum.valueOf((Class<T>) type, raw.trim().toUpperCase());
		} catch (Exception exception) {
			throw new PropertyConverterException(String.format("Invalid value '%s' for enum %s", raw, type.getName()),
					exception);
		}
	}

}
