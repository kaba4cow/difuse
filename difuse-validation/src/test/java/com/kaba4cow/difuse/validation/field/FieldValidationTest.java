package com.kaba4cow.difuse.validation.field;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.kaba4cow.difuse.core.annotation.context.DifuseContext;
import com.kaba4cow.difuse.core.test.DifuseTestExtension;
import com.kaba4cow.difuse.core.test.annotation.AssertThrows;
import com.kaba4cow.difuse.core.test.annotation.TestContext;

@DifuseContext
@TestContext(FieldValidationTest.class)
@ExtendWith(DifuseTestExtension.class)
public class FieldValidationTest {

	@Test
	public void validBeanDoesNotThrow(ValidFieldValidatedBean bean) {}

	@Test
	@AssertThrows
	public void invvalidBeanThrows(InvalidFieldValidatedBean bean) {}

}
