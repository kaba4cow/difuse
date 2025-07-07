package com.kaba4cow.difuse.core.property.converter.impl.primitive;

import com.kaba4cow.difuse.core.property.converter.PropertyConverter;

public class BytePropertyConverter implements PropertyConverter<Byte> {

	@Override
	public Class<Byte> getTargetType() {
		return Byte.class;
	}

	@Override
	public Byte convert(String raw) {
		return Byte.valueOf(raw);
	}

}
