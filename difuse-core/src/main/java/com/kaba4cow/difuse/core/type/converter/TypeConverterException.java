package com.kaba4cow.difuse.core.type.converter;

import com.kaba4cow.difuse.core.DifuseException;

public class TypeConverterException extends DifuseException {

	private static final long serialVersionUID = 1L;

	public TypeConverterException(String message, Throwable cause) {
		super(message, cause);
	}

	public TypeConverterException(String message) {
		super(message);
	}

	public TypeConverterException(Throwable cause) {
		super(cause);
	}

}
