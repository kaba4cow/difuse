package com.kaba4cow.difuse.core.injection.source.classes;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.kaba4cow.difuse.core.annotation.context.DifuseContext;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.test.DifuseTestExtension;
import com.kaba4cow.difuse.core.test.annotation.AssertThrows;
import com.kaba4cow.difuse.core.test.annotation.TestContext;

@DifuseContext
@TestContext(ClassBeanInjectionTest.class)
@ExtendWith(DifuseTestExtension.class)
public class ClassBeanInjectionTest {

	@Provided
	private ClassBean providedField;

	private ClassBean nonProvidedField;

	@Test
	public void providedFieldNotNull() {
		assertNotNull(providedField);
	}

	@Test
	public void nonProvidedFieldNull() {
		assertNull(nonProvidedField);
	}

	@Test
	public void beanParameterNotNull(ClassBean beanParameter) {
		assertNotNull(beanParameter);
	}

	@Test
	@AssertThrows(RuntimeException.class)
	public void nonBeanParameterThrows(String nonBeanParameter) {}

}
