package com.kaba4cow.difuse.core.bean.source.validator.support;

import java.util.Set;

import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.bean.source.BeanSource;
import com.kaba4cow.difuse.core.bean.source.validator.BeanSourceValidationException;
import com.kaba4cow.difuse.core.bean.source.validator.BeanSourceValidator;

@SystemBean
public class BeanSourceValidatorService {

	@Provided
	private BeanSourceValidatorRegistry validatorRegistry;

	public void validate(BeanSource<?> beanSource) {
		try {
			Set<BeanSourceValidator> validators = validatorRegistry.getValidators(beanSource);
			for (BeanSourceValidator validator : validators)
				validator.validate(beanSource);
		} catch (BeanSourceValidationException exception) {
			throw new BeanSourceValidationException(String.format("Invalid BeanSource: %s", beanSource), exception);
		} catch (Exception exception) {
			throw new BeanSourceValidationException(String.format("Could not validate BeanSource: %s", beanSource), exception);
		}
	}

}
