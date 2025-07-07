package com.kaba4cow.difuse.core.environment;

import com.kaba4cow.difuse.core.DifuseException;

public class EnvironmentException extends DifuseException {

	private static final long serialVersionUID = 1L;

	public EnvironmentException(String message, Throwable cause) {
		super(message, cause);
	}

	public EnvironmentException(String message) {
		super(message);
	}

	public EnvironmentException(Throwable cause) {
		super(cause);
	}

}
