package com.kaba4cow.difuse.core.property.converter.impl.big;

import java.math.BigDecimal;

import com.kaba4cow.difuse.core.property.converter.PropertyConverter;

public class BigDecimalPropertyConverter implements PropertyConverter<BigDecimal> {

	@Override
	public Class<BigDecimal> getTargetType() {
		return BigDecimal.class;
	}

	@Override
	public BigDecimal convert(String raw) {
		return new BigDecimal(raw);
	}

}
