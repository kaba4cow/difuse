package com.kaba4cow.difuse.validation.field;

import javax.validation.constraints.NotNull;

import com.kaba4cow.difuse.core.annotation.bean.Bean;
import com.kaba4cow.difuse.core.annotation.bean.Lazy;
import com.kaba4cow.difuse.validation.annotation.Validated;

@Validated
@Lazy
@Bean
public class ValidFieldValidatedBean {

	@NotNull
	public final String field = "Hello World";

}
