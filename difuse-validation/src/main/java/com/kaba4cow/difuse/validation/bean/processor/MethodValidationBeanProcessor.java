package com.kaba4cow.difuse.validation.bean.processor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.executable.ExecutableValidator;

import com.kaba4cow.difuse.core.bean.BeanLifecyclePhase;
import com.kaba4cow.difuse.core.bean.processor.BeanProcessor;
import com.kaba4cow.difuse.core.bean.provider.BeanProvider;
import com.kaba4cow.difuse.core.dependency.provider.DependencyProviderSession;
import com.kaba4cow.difuse.core.util.ProxyFactory;
import com.kaba4cow.difuse.validation.MethodValidationException;
import com.kaba4cow.difuse.validation.annotation.Validated;

public class MethodValidationBeanProcessor implements BeanProcessor {

	private final ExecutableValidator validator = Validation.buildDefaultValidatorFactory().getValidator().forExecutables();

	@Override
	public BeanLifecyclePhase getLifecyclePhase() {
		return BeanLifecyclePhase.PROXY_WRAPPING;
	}

	@Override
	public Object process(Object bean, BeanProvider<?> beanProvider, DependencyProviderSession session) {
		Class<?> beanClass = beanProvider.getBeanSource().getBeanClass();
		return beanClass.isAnnotationPresent(Validated.class)//
				? createBeanProxy(bean, beanClass)//
				: bean;
	}

	private Object createBeanProxy(Object bean, Class<?> beanClass) {
		return ProxyFactory.createProxy(beanClass, new MethodValidator(bean));
	}

	private class MethodValidator implements InvocationHandler {

		private final Object bean;

		private MethodValidator(Object bean) {
			this.bean = bean;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			checkValidation(method, "parameters", validator.validateParameters(bean, method, args));
			Object result = method.invoke(bean, args);
			checkValidation(method, "return value", validator.validateReturnValue(bean, method, result));
			return result;
		}

		private void checkValidation(Method method, String validationTarget, Set<ConstraintViolation<Object>> violations) {
			if (!violations.isEmpty())
				throw new MethodValidationException(method, validationTarget, violations);
		}

	}

}
