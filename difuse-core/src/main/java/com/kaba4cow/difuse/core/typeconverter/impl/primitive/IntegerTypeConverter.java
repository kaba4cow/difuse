package com.kaba4cow.difuse.core.typeconverter.impl.primitive;

import com.kaba4cow.difuse.core.typeconverter.PrimitiveTypeConverter;
import com.kaba4cow.difuse.core.typeconverter.TypeConverter;

public class IntegerTypeConverter implements TypeConverter<Integer>, PrimitiveTypeConverter {

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
