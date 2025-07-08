package com.kaba4cow.difuse.core.property.converter.impl.primitive;

import com.kaba4cow.difuse.core.property.converter.PrimitivePropertyConverter;
import com.kaba4cow.difuse.core.property.converter.PropertyConverter;

public class ShortPropertyConverter implements PropertyConverter<Short>, PrimitivePropertyConverter {

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
