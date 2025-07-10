package com.kaba4cow.difuse.core.type.converter.impl.primitive;

import com.kaba4cow.difuse.core.type.converter.PrimitiveTypeConverter;

public class IntegerTypeConverter implements PrimitiveTypeConverter<Integer> {

	@Override
	public Class<?> getPrimitiveTargetType() {
		return int.class;
	}

	@Override
	public Class<Integer> getTargetType() {
		return Integer.class;
	}

	@Override
	public Integer convert(String raw) {
		return Integer.valueOf(raw);
	}

}
