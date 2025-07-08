package com.kaba4cow.difuse.core.config.converter.impl.primitive;

import com.kaba4cow.difuse.core.config.converter.PrimitiveTypeConverter;
import com.kaba4cow.difuse.core.config.converter.TypeConverter;

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
