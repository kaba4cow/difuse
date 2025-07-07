package com.kaba4cow.difuse.core.property.converter.impl.primitive;

import com.kaba4cow.difuse.core.property.converter.PropertyConverter;

public class DoublePropertyConverter implements PropertyConverter<Double> {

	@Override
	public Class<Double> getTargetType() {
		return Double.class;
	}

	@Override
	public Double convert(String raw) {
		return Double.valueOf(raw);
	}

}
