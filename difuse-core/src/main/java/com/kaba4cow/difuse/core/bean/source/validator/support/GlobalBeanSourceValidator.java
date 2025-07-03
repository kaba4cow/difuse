package com.kaba4cow.difuse.core.bean.source.validator.support;

import java.util.Set;

import com.kaba4cow.difuse.core.annotation.application.CoreComponent;
import com.kaba4cow.difuse.core.annotation.application.CoreDependency;
import com.kaba4cow.difuse.core.bean.source.BeanSource;
import com.kaba4cow.difuse.core.bean.source.validator.BeanSourceValidator;

@CoreComponent
public class GlobalBeanSourceValidator {

	@CoreDependency
	private BeanSourceValidatorRegistry beanSourceValidatorRegistry;

	public void validate(BeanSource<?> beanSource) {
		Set<BeanSourceValidator> validators = beanSourceValidatorRegistry.getValidators(beanSource);
		for (BeanSourceValidator validator : validators)
			validator.validate(beanSource);
	}

}
