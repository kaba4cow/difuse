package com.kaba4cow.difuse.aop.bean.validator;

import com.kaba4cow.difuse.core.bean.source.BeanSource;
import com.kaba4cow.difuse.core.bean.source.validator.BeanSourceValidationException;
import com.kaba4cow.difuse.core.bean.source.validator.BeanSourceValidator;

public class AspectBeanSourceValidator implements BeanSourceValidator {

	@Override
	public void validate(BeanSource<?> beanSource) throws BeanSourceValidationException {}

}
