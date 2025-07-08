package com.kaba4cow.difuse.core.property.converter.impl.primitive;

import com.kaba4cow.difuse.core.property.converter.PrimitivePropertyConverter;
import com.kaba4cow.difuse.core.property.converter.PropertyConverter;

public class IntegerPropertyConverter implements PropertyConverter<Integer>, PrimitivePropertyConverter {

	@Override
	public Class<?> getPrimitiveTargetType() {
		return int.class;
	}

	@Override
	public Class<Integer> getTargetType() {
		return Integer.class;
	}

	@Override
	public Integer convert(String raw) {
		return Integer.valueOf(raw);
	}

}
