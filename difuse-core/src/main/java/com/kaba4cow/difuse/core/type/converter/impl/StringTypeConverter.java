package com.kaba4cow.difuse.core.type.converter.impl;

import com.kaba4cow.difuse.core.type.converter.TypeConverter;

public class StringTypeConverter implements TypeConverter<String> {

	@Override
	public Class<String> getTargetType() {
		return String.class;
	}

	@Override
	public String convert(String raw) {
		return raw;
	}

}
