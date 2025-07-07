package com.kaba4cow.difuse.core.property.converter.impl.big;

import java.math.BigInteger;

import com.kaba4cow.difuse.core.property.converter.PropertyConverter;

public class BigIntegerPropertyConverter implements PropertyConverter<BigInteger> {

	@Override
	public Class<BigInteger> getTargetType() {
		return BigInteger.class;
	}

	@Override
	public BigInteger convert(String raw) {
		return new BigInteger(raw);
	}

}
