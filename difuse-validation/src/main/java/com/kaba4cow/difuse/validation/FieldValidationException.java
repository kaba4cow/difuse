package com.kaba4cow.difuse.validation;

import java.util.Set;

import javax.validation.ConstraintViolation;

import com.kaba4cow.difuse.core.bean.processor.post.BeanPostProcessorException;

public class FieldValidationException extends BeanPostProcessorException {

	private static final long serialVersionUID = 1L;

	public FieldValidationException(Class<?> beanClass, Set<ConstraintViolation<Object>> violations) {
		super(buildMessage(beanClass, violations));
	}

	private static String buildMessage(Class<?> beanClass, Set<ConstraintViolation<Object>> violations) {
		StringBuilder message = new StringBuilder()//
				.append("Validation failed for bean ")//
				.append(beanClass.getName())//
				.append(":\n");
		for (ConstraintViolation<Object> violation : violations)
			message//
					.append(" - ")//
					.append(violation.getPropertyPath())//
					.append(": ")//
					.append(violation.getMessage())//
					.append("\n");
		return message.toString();
	}

}
