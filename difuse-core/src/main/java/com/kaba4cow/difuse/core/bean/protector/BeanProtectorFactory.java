package com.kaba4cow.difuse.core.bean.protector;

import java.lang.reflect.AnnotatedElement;

import com.kaba4cow.difuse.core.annotation.system.SystemComponent;
import com.kaba4cow.difuse.core.annotation.system.SystemDependency;
import com.kaba4cow.difuse.core.application.ApplicationParameters;

@SystemComponent
public class BeanProtectorFactory {

	@SystemDependency
	private ApplicationParameters applicationParameters;

	public BeanProtector createBeanProtector(AnnotatedElement... elements) {
		return applicationParameters.getTestClass().isPresent()//
				? createTestBeanProtector(elements)//
				: createDefaultBeanProtector(elements);
	}

	private BeanProtector createTestBeanProtector(AnnotatedElement... elements) {
		return new TestBeanProtector(applicationParameters.getSourceClass(), elements);
	}

	private BeanProtector createDefaultBeanProtector(AnnotatedElement... elements) {
		return new BeanProtector(elements);
	}

}
