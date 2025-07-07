package com.kaba4cow.difuse.core.environment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.kaba4cow.difuse.core.annotation.context.DifuseContext;
import com.kaba4cow.difuse.core.annotation.context.WithConfigs;
import com.kaba4cow.difuse.core.annotation.dependency.Property;
import com.kaba4cow.difuse.core.test.DifuseTestExtension;
import com.kaba4cow.difuse.core.test.annotation.AssertThrows;
import com.kaba4cow.difuse.core.test.annotation.TestContext;

@DifuseContext
@WithConfigs({ "conversion" })
@TestContext(PropertyConversionTest.class)
@ExtendWith(DifuseTestExtension.class)
public class PropertyConversionTest {

	@Test
	@AssertThrows
	public void unsupportedThrows(@Property("unsupported") Thread value) {}

	@Test
	public void convertsString(@Property("string") String stringValue) {
		assertEquals("Hello World", stringValue);
	}

	@Test
	public void convertsBoolean(@Property("boolean") boolean booleanValue) {
		assertEquals(true, booleanValue);
	}

	@Test
	public void convertsInt(@Property("int") int intValue) {
		assertEquals(256, intValue);
	}

	@Test
	public void convertsFloat(@Property("float") float floatValue) {
		assertEquals(3.14f, floatValue, 0.001f);
	}

	@Test
	public void convertsChar(@Property("char") char charValue) {
		assertEquals('c', charValue);
	}

}
