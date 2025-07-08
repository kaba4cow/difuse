package com.kaba4cow.difuse.core.config.converter.impl.primitive;

import com.kaba4cow.difuse.core.config.converter.PrimitivePropertyConverter;
import com.kaba4cow.difuse.core.config.converter.PropertyConverter;

public class DoublePropertyConverter implements PropertyConverter<Double>, PrimitivePropertyConverter {

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
