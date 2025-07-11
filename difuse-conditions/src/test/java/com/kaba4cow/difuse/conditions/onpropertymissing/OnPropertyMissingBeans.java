package com.kaba4cow.difuse.conditions.onpropertymissing;

import com.kaba4cow.difuse.conditions.annotation.OnPropertyExists;
import com.kaba4cow.difuse.core.annotation.bean.Bean;

public class OnPropertyMissingBeans {

	@OnPropertyExists("missing")
	@Bean
	public static class PassingBean {}

	@OnPropertyExists("greeting")
	@Bean
	public static class FailingBean {}

}
