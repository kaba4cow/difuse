package com.kaba4cow.difuse.core.bean.processor;

import com.kaba4cow.difuse.core.DifuseException;

public class BeanProcessorException extends DifuseException {

	private static final long serialVersionUID = 1L;

	public BeanProcessorException(String message, Throwable cause) {
		super(message, cause);
	}

	public BeanProcessorException(String message) {
		super(message);
	}

	public BeanProcessorException(Throwable cause) {
		super(cause);
	}

}
