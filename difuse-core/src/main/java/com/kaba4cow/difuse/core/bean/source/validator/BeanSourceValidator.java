package com.kaba4cow.difuse.core.bean.source.validator;

import com.kaba4cow.difuse.core.bean.source.BeanSource;

public interface BeanSourceValidator {

	void validate(BeanSource<?> beanSource) throws BeanSourceValidationException;

}
