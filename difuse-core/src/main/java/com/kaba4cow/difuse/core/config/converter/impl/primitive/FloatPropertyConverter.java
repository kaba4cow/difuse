package com.kaba4cow.difuse.core.config.converter.impl.primitive;

import com.kaba4cow.difuse.core.config.converter.PrimitivePropertyConverter;
import com.kaba4cow.difuse.core.config.converter.PropertyConverter;

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
