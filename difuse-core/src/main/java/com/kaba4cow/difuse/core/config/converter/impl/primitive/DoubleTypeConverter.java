package com.kaba4cow.difuse.core.config.converter.impl.primitive;

import com.kaba4cow.difuse.core.config.converter.PrimitiveTypeConverter;
import com.kaba4cow.difuse.core.config.converter.TypeConverter;

public class DoubleTypeConverter implements TypeConverter<Double>, PrimitiveTypeConverter {

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
