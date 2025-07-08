package com.kaba4cow.difuse.core.property.converter.impl.primitive;

import com.kaba4cow.difuse.core.property.converter.PrimitivePropertyConverter;
import com.kaba4cow.difuse.core.property.converter.PropertyConverter;

public class BooleanPropertyConverter implements PropertyConverter<Boolean>, PrimitivePropertyConverter {

	@Override
	public Class<?> getPrimitiveTargetType() {
		return boolean.class;
	}

	@Override
	public Class<Boolean> getTargetType() {
		return Boolean.class;
	}

	@Override
	public Boolean convert(String raw) {
		return Boolean.valueOf(raw);
	}

}
