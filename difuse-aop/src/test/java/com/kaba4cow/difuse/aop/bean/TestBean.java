package com.kaba4cow.difuse.aop.bean;

import com.kaba4cow.difuse.aop.Call;
import com.kaba4cow.difuse.aop.targets.Logged;
import com.kaba4cow.difuse.core.annotation.bean.Bean;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;

@Bean
public class TestBean {

	@Provided
	private CallHistory callHistory;

	@Logged
	public String beanMethod() {
		callHistory.addCall(Call.BEAN_METHOD);
		return "Hello World!";
	}

}
