package com.kaba4cow.difuse.core.type.converter.impl.primitive;

import com.kaba4cow.difuse.core.type.converter.PrimitiveTypeConverter;
import com.kaba4cow.difuse.core.type.converter.TypeConverter;
import com.kaba4cow.difuse.core.type.converter.TypeConverterException;

public class CharTypeConverter implements TypeConverter<Character>, PrimitiveTypeConverter {

	@Override
	public Class<?> getPrimitiveTargetType() {
		return char.class;
	}

	@Override
	public Class<Character> getTargetType() {
		return Character.class;
	}

	@Override
	public Character convert(String raw) {
		if (raw.isEmpty())
			throw new TypeConverterException("Cannot convert empty string to char");
		else if (raw.length() > 1)
			throw new TypeConverterException(String.format("String is too long to convert to char: '%s'", raw));
		else
			return raw.charAt(0);
	}

}
