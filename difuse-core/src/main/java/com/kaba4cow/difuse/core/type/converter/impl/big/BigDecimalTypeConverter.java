package com.kaba4cow.difuse.core.type.converter.impl.big;

import java.math.BigDecimal;

import com.kaba4cow.difuse.core.type.converter.TypeConverter;

public class BigDecimalTypeConverter implements TypeConverter<BigDecimal> {

	@Override
	public Class<BigDecimal> getTargetType() {
		return BigDecimal.class;
	}

	@Override
	public BigDecimal convert(String raw) {
		return new BigDecimal(raw);
	}

}
