package com.kaba4cow.difuse.core.bean.protector;

import java.lang.reflect.AnnotatedElement;

import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.system.SystemParameters;

@SystemBean
public class BeanProtectorFactory {

	@Provided
	private SystemParameters systemParameters;

	public BeanProtector createBeanProtector(AnnotatedElement... elements) {
		return systemParameters.getTestClass().isPresent()//
				? createTestBeanProtector(elements)//
				: createDefaultBeanProtector(elements);
	}

	private BeanProtector createTestBeanProtector(AnnotatedElement... elements) {
		return new TestBeanProtector(systemParameters.getSourceClass(), elements);
	}

	private BeanProtector createDefaultBeanProtector(AnnotatedElement... elements) {
		return new BeanProtector(elements);
	}

}
