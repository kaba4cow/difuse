package com.kaba4cow.difuse.core.property.converter;

import com.kaba4cow.difuse.core.DifuseException;

public class PropertyConverterException extends DifuseException {

	private static final long serialVersionUID = 1L;

	public PropertyConverterException(String message, Throwable cause) {
		super(message, cause);
	}

	public PropertyConverterException(String message) {
		super(message);
	}

	public PropertyConverterException(Throwable cause) {
		super(cause);
	}

}
