package com.kaba4cow.difuse.core.typeconverter.support.converter;

import java.lang.reflect.Type;
import java.util.Objects;
import java.util.Optional;

import com.kaba4cow.difuse.core.typeconverter.support.GlobalTypeConverter;

public class OptionalConverter {

	private final GlobalTypeConverter typeConverter;

	public OptionalConverter(GlobalTypeConverter typeConverter) {
		this.typeConverter = typeConverter;
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
		Object value = typeConverter.convert(raw, itemType);
		return Optional.ofNullable(value);
	}

}
