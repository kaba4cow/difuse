package com.kaba4cow.difuse.core.config.converter.impl.primitive;

import com.kaba4cow.difuse.core.config.converter.PrimitiveTypeConverter;
import com.kaba4cow.difuse.core.config.converter.TypeConverter;

public class FloatTypeConverter implements TypeConverter<Float>, PrimitiveTypeConverter {

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
