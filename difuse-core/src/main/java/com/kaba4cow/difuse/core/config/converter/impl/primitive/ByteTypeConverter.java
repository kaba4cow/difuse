package com.kaba4cow.difuse.core.config.converter.impl.primitive;

import com.kaba4cow.difuse.core.config.converter.PrimitiveTypeConverter;
import com.kaba4cow.difuse.core.config.converter.TypeConverter;

public class ByteTypeConverter implements TypeConverter<Byte>, PrimitiveTypeConverter {

	@Override
	public Class<?> getPrimitiveTargetType() {
		return byte.class;
	}

	@Override
	public Class<Byte> getTargetType() {
		return Byte.class;
	}

	@Override
	public Byte convert(String raw) {
		return Byte.valueOf(raw);
	}

}
