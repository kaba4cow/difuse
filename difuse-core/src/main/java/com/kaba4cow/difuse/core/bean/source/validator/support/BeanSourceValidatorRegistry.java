package com.kaba4cow.difuse.core.bean.source.validator.support;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.application.CoreComponent;
import com.kaba4cow.difuse.core.bean.source.BeanSource;
import com.kaba4cow.difuse.core.bean.source.validator.BeanSourceValidator;

@CoreComponent
public class BeanSourceValidatorRegistry {

	private static final Logger log = LoggerFactory.getLogger("BeanSourceValidatorRegistry");

	private final Set<BeanSourceValidator> registry = ConcurrentHashMap.newKeySet();

	public void registerValidator(BeanSourceValidator validator) {
		registry.add(validator);
		log.debug("Registered BeanSourceValidator {}", validator.getClass());
	}

	public Set<BeanSourceValidator> getValidators(BeanSource<?> beanSource) {
		return Collections.unmodifiableSet(registry);
	}

}
