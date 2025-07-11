package com.kaba4cow.difuse.conditions.onpropertyequals;

import com.kaba4cow.difuse.conditions.annotation.OnPropertyEquals;
import com.kaba4cow.difuse.core.annotation.bean.Bean;

public class OnPropertyEqualsBeans {

	@OnPropertyEquals(key = "greeting", value = "Hello World")
	@Bean
	public static class PassingBean {}

	@OnPropertyEquals(key = "greeting", value = "Hello Sailor")
	@Bean
	public static class FailingBean {}

}
