package com.kaba4cow.difuse.validation.bean.processor.post;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.kaba4cow.difuse.core.bean.processor.post.phase.RuntimeBeanPostProcessor;
import com.kaba4cow.difuse.core.bean.provider.impl.ClassBeanProvider;
import com.kaba4cow.difuse.core.dependency.provider.DependencyProviderSession;
import com.kaba4cow.difuse.validation.FieldValidationException;
import com.kaba4cow.difuse.validation.annotation.Validated;

public class FieldValidationRuntimeBeanPostProcessor extends RuntimeBeanPostProcessor {

	private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	@Override
	public Object postProcess(Object bean, ClassBeanProvider beanProvider, DependencyProviderSession session) {
		Class<?> beanClass = beanProvider.getBeanSource().getBeanClass();
		if (beanClass.isAnnotationPresent(Validated.class))
			checkValidation(beanClass, validator.validate(bean));
		return bean;
	}

	private void checkValidation(Class<?> beanClass, Set<ConstraintViolation<Object>> violations) {
		if (!violations.isEmpty())
			throw new FieldValidationException(beanClass, violations);
	}

}
