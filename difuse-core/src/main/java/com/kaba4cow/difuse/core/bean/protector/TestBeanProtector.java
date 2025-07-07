package com.kaba4cow.difuse.core.bean.protector;

import com.kaba4cow.difuse.core.dependency.DependencyConsumer;

public class TestBeanProtector extends BeanProtector {

	private final Class<?> testClass;

	public TestBeanProtector(Class<?> testClass) {
		this.testClass = testClass;
	}

	@Override
	public boolean canBeAccessedBy(DependencyConsumer consumer) {
		return testClass == consumer.getContextSource().getSourceClass() || super.canBeAccessedBy(consumer);
	}

}
