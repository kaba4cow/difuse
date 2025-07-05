package com.kaba4cow.difuse.validation.parameter;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.kaba4cow.difuse.core.annotation.context.DifuseContext;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.test.DifuseTestExtension;
import com.kaba4cow.difuse.core.test.annotation.AssertThrows;
import com.kaba4cow.difuse.core.test.annotation.TestContext;
import com.kaba4cow.difuse.validation.MethodValidationException;

@DifuseContext
@TestContext(ParameterValidationTest.class)
@ExtendWith(DifuseTestExtension.class)
public class ParameterValidationTest {

	@Provided
	private ParameterValidatedBean bean;

	@Test
	public void notNullParameterDoesNotThrow() {
		assertDoesNotThrow(() -> bean.acceptsNonNull("valid"));
	}

	@AssertThrows(MethodValidationException.class)
	@Test
	public void nullParameterThrows() {
		bean.acceptsNonNull(null);
	}

	@Test
	public void validSizedParameterDoesNotThrow() {
		assertDoesNotThrow(() -> bean.acceptsSized("abc"));
	}

	@AssertThrows(MethodValidationException.class)
	@Test
	public void invalidSizedParameterThrows() {
		bean.acceptsSized("ab");
	}

	@Test
	public void validEmailParameterDoesNotThrow() {
		assertDoesNotThrow(() -> bean.acceptsEmail("example@mail.com"));
	}

	@AssertThrows(MethodValidationException.class)
	@Test
	public void invalidEmailParameterThrows() {
		bean.acceptsEmail("notAnEmail");
	}

}
