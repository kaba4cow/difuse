package com.kaba4cow.difuse.core.typeconverter.impl.primitive;

import com.kaba4cow.difuse.core.typeconverter.PrimitiveTypeConverter;
import com.kaba4cow.difuse.core.typeconverter.TypeConverter;

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
