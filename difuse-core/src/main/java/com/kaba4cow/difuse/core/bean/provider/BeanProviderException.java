package com.kaba4cow.difuse.core.bean.provider;

import com.kaba4cow.difuse.core.DifuseException;

public class BeanProviderException extends DifuseException {

	private static final long serialVersionUID = 1L;

	public BeanProviderException(String message, Throwable cause) {
		super(message, cause);
	}

	public BeanProviderException(String message) {
		super(message);
	}

	public BeanProviderException(Throwable cause) {
		super(cause);
	}

}
