package com.kaba4cow.difuse.core.bean.access.restrictor;

import com.kaba4cow.difuse.core.DifuseException;

public class BeanAccessRestrictorException extends DifuseException {

	private static final long serialVersionUID = 1L;

	public BeanAccessRestrictorException(String message, Throwable cause) {
		super(message, cause);
	}

	public BeanAccessRestrictorException(String message) {
		super(message);
	}

	public BeanAccessRestrictorException(Throwable cause) {
		super(cause);
	}

}
