package com.kaba4cow.difuse.core.config.converter.impl.primitive;

import com.kaba4cow.difuse.core.config.converter.PrimitivePropertyConverter;
import com.kaba4cow.difuse.core.config.converter.PropertyConverter;

public class BytePropertyConverter implements PropertyConverter<Byte>, PrimitivePropertyConverter {

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
