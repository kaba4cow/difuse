package com.kaba4cow.difuse.core.type.converter.impl.primitive;

import com.kaba4cow.difuse.core.type.converter.PrimitiveTypeConverter;
import com.kaba4cow.difuse.core.type.converter.TypeConverter;

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
