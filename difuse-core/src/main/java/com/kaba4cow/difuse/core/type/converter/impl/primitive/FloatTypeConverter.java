package com.kaba4cow.difuse.core.type.converter.impl.primitive;

import com.kaba4cow.difuse.core.type.converter.PrimitiveTypeConverter;

public class FloatTypeConverter implements PrimitiveTypeConverter<Float> {

	@Override
	public Class<?> getPrimitiveTargetType() {
		return float.class;
	}

	@Override
	public Class<Float> getTargetType() {
		return Float.class;
	}

	@Override
	public Float convert(String raw) {
		return Float.valueOf(raw);
	}

}
