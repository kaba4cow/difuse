package com.kaba4cow.difuse.core.bean.protector;

import com.kaba4cow.difuse.core.DifuseException;

public class BeanProtectorException extends DifuseException {

	private static final long serialVersionUID = 1L;

	public BeanProtectorException(String message, Throwable cause) {
		super(message, cause);
	}

	public BeanProtectorException(String message) {
		super(message);
	}

	public BeanProtectorException(Throwable cause) {
		super(cause);
	}

}
