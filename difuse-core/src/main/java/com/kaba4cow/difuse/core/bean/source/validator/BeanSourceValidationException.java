package com.kaba4cow.difuse.core.bean.source.validator;

public class BeanSourceValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BeanSourceValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public BeanSourceValidationException(String message) {
		super(message);
	}

	public BeanSourceValidationException(Throwable cause) {
		super(cause);
	}

}
