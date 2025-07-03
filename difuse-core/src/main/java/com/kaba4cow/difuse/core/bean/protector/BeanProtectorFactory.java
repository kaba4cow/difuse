package com.kaba4cow.difuse.core.bean.protector;

import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;

import com.kaba4cow.difuse.core.annotation.application.CoreComponent;
import com.kaba4cow.difuse.core.annotation.application.CoreDependency;
import com.kaba4cow.difuse.core.application.ApplicationParameters;

@CoreComponent
public class BeanProtectorFactory {

	@CoreDependency
	private ApplicationParameters applicationParameters;

	public BeanProtector createBeanProtector(AnnotatedElement... elements) {
		return applicationParameters.getTestClass().isPresent()//
				? createTestBeanProtector(elements)//
				: createDefaultBeanProtector(elements);
	}

	private BeanProtector createTestBeanProtector(AnnotatedElement... elements) {
		return new TestBeanProtector(applicationParameters.getSourceClass(), Arrays.asList(elements));
	}

	private BeanProtector createDefaultBeanProtector(AnnotatedElement... elements) {
		return new BeanProtector(Arrays.asList(elements));
	}

}
