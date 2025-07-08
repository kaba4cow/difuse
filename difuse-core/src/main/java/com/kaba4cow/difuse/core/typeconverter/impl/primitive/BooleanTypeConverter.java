package com.kaba4cow.difuse.core.typeconverter.impl.primitive;

import com.kaba4cow.difuse.core.typeconverter.PrimitiveTypeConverter;
import com.kaba4cow.difuse.core.typeconverter.TypeConverter;

public class BooleanTypeConverter implements TypeConverter<Boolean>, PrimitiveTypeConverter {

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
