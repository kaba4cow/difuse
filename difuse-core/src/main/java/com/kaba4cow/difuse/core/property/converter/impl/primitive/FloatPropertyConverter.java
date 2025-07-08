package com.kaba4cow.difuse.core.property.converter.impl.primitive;

import com.kaba4cow.difuse.core.property.converter.PrimitivePropertyConverter;
import com.kaba4cow.difuse.core.property.converter.PropertyConverter;

public class FloatPropertyConverter implements PropertyConverter<Float>, PrimitivePropertyConverter {

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
