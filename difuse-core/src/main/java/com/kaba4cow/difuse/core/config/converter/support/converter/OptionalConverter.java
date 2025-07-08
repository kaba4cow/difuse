package com.kaba4cow.difuse.core.config.converter.support.converter;

import java.lang.reflect.Type;
import java.util.Objects;
import java.util.Optional;

import com.kaba4cow.difuse.core.config.converter.support.GlobalPropertyConverter;

public class OptionalConverter {

	private final GlobalPropertyConverter propertyConverter;

	public OptionalConverter(GlobalPropertyConverter propertyConverter) {
		this.propertyConverter = propertyConverter;
	}

	public Object convert(Object raw, Type itemType) {
		if (Objects.isNull(raw))
			return Optional.empty();
		else if (raw instanceof Optional<?>) {
			Optional<?> optional = (Optional<?>) raw;
			if (optional.isPresent())
				raw = optional.get();
			else
				return Optional.empty();
		}
		Object value = propertyConverter.convert(raw, itemType);
		return Optional.ofNullable(value);
	}

}
