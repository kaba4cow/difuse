package com.kaba4cow.difuse.core.type.converter.impl.primitive;

import com.kaba4cow.difuse.core.type.converter.PrimitiveTypeConverter;

public class DoubleTypeConverter implements PrimitiveTypeConverter<Double> {

	@Override
	public Class<?> getPrimitiveTargetType() {
		return double.class;
	}

	@Override
	public Class<Double> getTargetType() {
		return Double.class;
	}

	@Override
	public Double convert(String raw) {
		return Double.valueOf(raw);
	}

}
