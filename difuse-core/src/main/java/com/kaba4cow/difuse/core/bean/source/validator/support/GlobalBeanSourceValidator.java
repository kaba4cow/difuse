package com.kaba4cow.difuse.core.bean.source.validator.support;

import java.util.Set;

import com.kaba4cow.difuse.core.annotation.system.SystemComponent;
import com.kaba4cow.difuse.core.annotation.system.SystemDependency;
import com.kaba4cow.difuse.core.bean.source.BeanSource;
import com.kaba4cow.difuse.core.bean.source.validator.BeanSourceValidationException;
import com.kaba4cow.difuse.core.bean.source.validator.BeanSourceValidator;

@SystemComponent
public class GlobalBeanSourceValidator {

	@SystemDependency
	private BeanSourceValidatorRegistry beanSourceValidatorRegistry;

	public void validate(BeanSource<?> beanSource) {
		try {
			Set<BeanSourceValidator> validators = beanSourceValidatorRegistry.getValidators(beanSource);
			for (BeanSourceValidator validator : validators)
				validator.validate(beanSource);
		} catch (BeanSourceValidationException exception) {
			throw new BeanSourceValidationException(String.format("Invalid BeanSource: %s", beanSource), exception);
		} catch (Exception exception) {
			throw new BeanSourceValidationException(String.format("Could not validate BeanSource: %s", beanSource), exception);
		}
	}

}
