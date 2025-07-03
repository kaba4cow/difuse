package com.kaba4cow.difuse.core;

public class DifuseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DifuseException() {
		super();
	}

	public DifuseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DifuseException(String message, Throwable cause) {
		super(message, cause);
	}

	public DifuseException(String message) {
		super(message);
	}

	public DifuseException(Throwable cause) {
		super(cause);
	}

}
