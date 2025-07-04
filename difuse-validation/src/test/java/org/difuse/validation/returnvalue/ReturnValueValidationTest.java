package org.difuse.validation.returnvalue;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.difuse.validation.MethodValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.kaba4cow.difuse.core.annotation.context.DifuseContext;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.test.DifuseTestExtension;
import com.kaba4cow.difuse.core.test.annotation.AssertThrows;
import com.kaba4cow.difuse.core.test.annotation.TestContext;

@DifuseContext
@TestContext(ReturnValueValidationTest.class)
@ExtendWith(DifuseTestExtension.class)
public class ReturnValueValidationTest {

	@Provided
	private ReturnValueValidatedBean bean;

	@Test
	public void returnsObjectDoesNotThrow() {
		assertDoesNotThrow(() -> bean.returnsObject());
	}

	@AssertThrows(MethodValidationException.class)
	@Test
	public void returnsNullThrows() {
		bean.returnsNull();
	}

	@Test
	public void returnsValidEmailDoesNotThrow() {
		assertDoesNotThrow(() -> bean.returnsValidEmail());
	}

	@AssertThrows(MethodValidationException.class)
	@Test
	public void returnsInvalidEmailThrows() {
		bean.returnsInvalidEmail();
	}

}
