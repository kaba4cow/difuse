package com.kaba4cow.difuse.core.config.converter.impl.primitive;

import com.kaba4cow.difuse.core.config.converter.PrimitivePropertyConverter;
import com.kaba4cow.difuse.core.config.converter.PropertyConverter;
import com.kaba4cow.difuse.core.config.converter.PropertyConverterException;

public class CharPropertyConverter implements PropertyConverter<Character>, PrimitivePropertyConverter {

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
			throw new PropertyConverterException("Cannot convert empty string to char");
		else if (raw.length() > 1)
			throw new PropertyConverterException(String.format("String is too long to convert to char: '%s'", raw));
		else
			return raw.charAt(0);
	}

}
