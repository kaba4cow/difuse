package com.kaba4cow.difuse.aspects.bean;

import com.kaba4cow.difuse.aspects.Call;
import com.kaba4cow.difuse.aspects.targets.Logged;
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
