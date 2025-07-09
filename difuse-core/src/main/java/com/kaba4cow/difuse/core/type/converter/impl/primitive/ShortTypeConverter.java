package com.kaba4cow.difuse.core.type.converter.impl.primitive;

import com.kaba4cow.difuse.core.type.converter.PrimitiveTypeConverter;
import com.kaba4cow.difuse.core.type.converter.TypeConverter;

public class ShortTypeConverter implements TypeConverter<Short>, PrimitiveTypeConverter {

	@Override
	public Class<?> getPrimitiveTargetType() {
		return short.class;
	}

	@Override
	public Class<Short> getTargetType() {
		return Short.class;
	}

	@Override
	public Short convert(String raw) {
		return Short.valueOf(raw);
	}

}
