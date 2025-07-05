package com.kaba4cow.difuse.aop;

import com.kaba4cow.difuse.core.annotation.bean.Bean;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;

@Bean
public class TestBean {

	@Provided
	private AspectFlags flags;

	@AspectTarget
	public void annotatedMethod() {
		flags.methodInvoked();
	}

	public void plainMethod() {}

}
