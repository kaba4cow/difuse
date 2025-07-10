package com.kaba4cow.difuse.core.type.converter.impl.primitive;

import com.kaba4cow.difuse.core.type.converter.PrimitiveTypeConverter;

public class ShortTypeConverter implements PrimitiveTypeConverter<Short> {

	@Override
	public Class<?> getPrimitiveTargetType() {
		return short.class;
	}

	@Override
	public Class<Short> getTargetType() {
		return Short.class;
	}

	@Override
	public Short convert(String raw) {
		return Short.valueOf(raw);
	}

}
