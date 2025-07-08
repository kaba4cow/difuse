package com.kaba4cow.difuse.core.property.converter.impl.primitive;

import com.kaba4cow.difuse.core.property.converter.PrimitivePropertyConverter;
import com.kaba4cow.difuse.core.property.converter.PropertyConverter;

public class LongPropertyConverter implements PropertyConverter<Long>, PrimitivePropertyConverter {

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
