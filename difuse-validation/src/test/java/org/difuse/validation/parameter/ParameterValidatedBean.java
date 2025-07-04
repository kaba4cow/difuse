package org.difuse.validation.parameter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.difuse.validation.annotation.Validated;

import com.kaba4cow.difuse.core.annotation.bean.Bean;

@Validated
@Bean
public class ParameterValidatedBean {

	public void acceptsNonNull(@NotNull String input) {}

	public void acceptsSized(@Size(min = 3) String input) {}

	public void acceptsEmail(@Email String input) {}

}
