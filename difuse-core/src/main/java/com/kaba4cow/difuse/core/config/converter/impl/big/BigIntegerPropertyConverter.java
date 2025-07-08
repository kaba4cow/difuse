package com.kaba4cow.difuse.core.config.converter.impl.big;

import java.math.BigInteger;

import com.kaba4cow.difuse.core.config.converter.PropertyConverter;

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
