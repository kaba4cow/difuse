package com.kaba4cow.difuse.scheduling.bean.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import com.kaba4cow.difuse.core.bean.source.BeanSource;
import com.kaba4cow.difuse.core.bean.source.impl.ClassBeanSource;
import com.kaba4cow.difuse.core.bean.source.validator.BeanSourceValidationException;
import com.kaba4cow.difuse.core.bean.source.validator.BeanSourceValidator;
import com.kaba4cow.difuse.core.util.reflections.MethodScanner;
import com.kaba4cow.difuse.scheduling.annotation.task.CronScheduled;
import com.kaba4cow.difuse.scheduling.annotation.task.FixedDelayScheduled;
import com.kaba4cow.difuse.scheduling.annotation.task.FixedRateScheduled;

public class ScheduledBeanSourceValidator implements BeanSourceValidator {

	private final List<Class<? extends Annotation>> annotationTypes = Arrays.asList(//
			FixedDelayScheduled.class, //
			FixedRateScheduled.class, //
			CronScheduled.class //
	);

	@Override
	public void validate(BeanSource<?> beanSource) throws BeanSourceValidationException {
		if (beanSource instanceof ClassBeanSource == false)
			return;
		Class<?> beanClass = beanSource.getDeclaringClass();
		for (Method method : MethodScanner.of(beanClass).findMethods()) {
			boolean scheduled = false;
			for (Class<? extends Annotation> annotationType : annotationTypes)
				if (method.isAnnotationPresent(annotationType)) {
					if (scheduled)
						throw new BeanSourceValidationException(
								String.format("Multiple scheduled annotations on method %s", method));
					scheduled = true;
				}
		}
	}

}
