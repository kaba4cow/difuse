package com.kaba4cow.difuse.core.bean.processor.pre;

import com.kaba4cow.difuse.core.DifuseException;

public class BeanPreProcessorException extends DifuseException {

	private static final long serialVersionUID = 1L;

	public BeanPreProcessorException(String message, Throwable cause) {
		super(message, cause);
	}

	public BeanPreProcessorException(String message) {
		super(message);
	}

	public BeanPreProcessorException(Throwable cause) {
		super(cause);
	}

}
