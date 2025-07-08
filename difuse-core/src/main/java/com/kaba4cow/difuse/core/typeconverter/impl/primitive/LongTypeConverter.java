package com.kaba4cow.difuse.core.typeconverter.impl.primitive;

import com.kaba4cow.difuse.core.typeconverter.PrimitiveTypeConverter;
import com.kaba4cow.difuse.core.typeconverter.TypeConverter;

public class LongTypeConverter implements TypeConverter<Long>, PrimitiveTypeConverter {

	@Override
	public Class<?> getPrimitiveTargetType() {
		return long.class;
	}

	@Override
	public Class<Long> getTargetType() {
		return Long.class;
	}

	@Override
	public Long convert(String raw) {
		return Long.valueOf(raw);
	}

}
