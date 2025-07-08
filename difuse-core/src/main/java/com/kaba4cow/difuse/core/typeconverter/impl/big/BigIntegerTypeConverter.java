package com.kaba4cow.difuse.core.typeconverter.impl.big;

import java.math.BigInteger;

import com.kaba4cow.difuse.core.typeconverter.TypeConverter;

public class BigIntegerTypeConverter implements TypeConverter<BigInteger> {

	@Override
	public Class<BigInteger> getTargetType() {
		return BigInteger.class;
	}

	@Override
	public BigInteger convert(String raw) {
		return new BigInteger(raw);
	}

}
