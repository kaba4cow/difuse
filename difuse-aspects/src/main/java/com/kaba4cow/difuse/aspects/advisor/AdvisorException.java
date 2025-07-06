package com.kaba4cow.difuse.aspects.advisor;

import com.kaba4cow.difuse.core.DifuseException;

public class AdvisorException extends DifuseException {

	private static final long serialVersionUID = 1L;

	public AdvisorException(String message, Throwable cause) {
		super(message, cause);
	}

	public AdvisorException(String message) {
		super(message);
	}

	public AdvisorException(Throwable cause) {
		super(cause);
	}

}
