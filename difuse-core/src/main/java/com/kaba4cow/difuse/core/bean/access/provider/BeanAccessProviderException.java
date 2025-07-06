package com.kaba4cow.difuse.core.bean.access.provider;

import com.kaba4cow.difuse.core.DifuseException;

public class BeanAccessProviderException extends DifuseException {

	private static final long serialVersionUID = 1L;

	public BeanAccessProviderException(String message, Throwable cause) {
		super(message, cause);
	}

	public BeanAccessProviderException(String message) {
		super(message);
	}

	public BeanAccessProviderException(Throwable cause) {
		super(cause);
	}

}
