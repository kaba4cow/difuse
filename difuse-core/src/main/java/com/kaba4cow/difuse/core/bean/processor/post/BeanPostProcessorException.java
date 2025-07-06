package com.kaba4cow.difuse.core.bean.processor.post;

import com.kaba4cow.difuse.core.DifuseException;

public class BeanPostProcessorException extends DifuseException {

	private static final long serialVersionUID = 1L;

	public BeanPostProcessorException(String message, Throwable cause) {
		super(message, cause);
	}

	public BeanPostProcessorException(String message) {
		super(message);
	}

	public BeanPostProcessorException(Throwable cause) {
		super(cause);
	}

}
