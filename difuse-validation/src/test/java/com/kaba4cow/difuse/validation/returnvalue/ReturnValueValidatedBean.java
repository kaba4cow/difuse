package com.kaba4cow.difuse.validation.returnvalue;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.kaba4cow.difuse.core.annotation.bean.Bean;
import com.kaba4cow.difuse.validation.annotation.Validated;

@Validated
@Bean
public class ReturnValueValidatedBean {

	@NotNull
	public Object returnsObject() {
		return new Object();
	}

	@NotNull
	public Object returnsNull() {
		return null;
	}

	@Email
	public String returnsValidEmail() {
		return "example@mail.com";
	}

	@Email
	public String returnsInvalidEmail() {
		return "notAnEmail";
	}

}
