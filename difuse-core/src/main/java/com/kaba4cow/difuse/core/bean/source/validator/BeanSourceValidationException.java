package com.kaba4cow.difuse.core.bean.source.validator;

public class BeanSourceValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BeanSourceValidationException() {
		super();
	}

	public BeanSourceValidationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

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
