package com.kaba4cow.difuse.conditions.onpropertyexists;

import com.kaba4cow.difuse.conditions.annotation.OnPropertyExists;
import com.kaba4cow.difuse.core.annotation.bean.Bean;

public class OnPropertyExistsBeans {

	@OnPropertyExists("greeting")
	@Bean
	public static class PassingBean {}

	@OnPropertyExists("missing")
	@Bean
	public static class FailingBean {}

}
