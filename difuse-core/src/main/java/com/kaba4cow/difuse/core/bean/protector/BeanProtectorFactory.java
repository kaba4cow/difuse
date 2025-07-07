package com.kaba4cow.difuse.core.bean.protector;

import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.system.SystemParameters;

@SystemBean
public class BeanProtectorFactory {

	@Provided
	private SystemParameters systemParameters;

	public BeanProtector createBeanProtector() {
		return systemParameters.getTestClass().isPresent()//
				? new TestBeanProtector(systemParameters.getSourceClass())//
				: new BeanProtector();
	}

}
