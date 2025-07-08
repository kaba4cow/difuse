package com.kaba4cow.difuse.core.config.converter.impl.primitive;

import com.kaba4cow.difuse.core.config.converter.PrimitiveTypeConverter;
import com.kaba4cow.difuse.core.config.converter.TypeConverter;

public class IntegerTypeConverter implements TypeConverter<Integer>, PrimitiveTypeConverter {

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
