package org.difuse.validation;

import java.lang.reflect.Method;
import java.util.Set;

import javax.validation.ConstraintViolation;

import com.kaba4cow.difuse.core.bean.processor.BeanProcessorException;

public class MethodValidationException extends BeanProcessorException {

	private static final long serialVersionUID = 1L;

	public MethodValidationException(Method method, String validationTarget, Set<ConstraintViolation<Object>> violations) {
		super(buildMessage(method, validationTarget, violations));
	}

	private static String buildMessage(Method method, String validationTarget, Set<ConstraintViolation<Object>> violations) {
		StringBuilder message = new StringBuilder()//
				.append("Validation failed for ")//
				.append(validationTarget)//
				.append(" of method ")//
				.append(method.getName())//
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
