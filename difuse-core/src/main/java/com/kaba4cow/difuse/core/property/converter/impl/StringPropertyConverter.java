package com.kaba4cow.difuse.core.property.converter.impl;

import com.kaba4cow.difuse.core.property.converter.PropertyConverter;

public class StringPropertyConverter implements PropertyConverter<String> {

	@Override
	public Class<String> getTargetType() {
		return String.class;
	}

	@Override
	public String convert(String raw) {
		return raw;
	}

}
