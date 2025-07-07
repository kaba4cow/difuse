package com.kaba4cow.difuse.core.environment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.kaba4cow.difuse.core.annotation.context.DifuseContext;
import com.kaba4cow.difuse.core.annotation.context.WithConfigs;
import com.kaba4cow.difuse.core.annotation.context.WithProfiles;
import com.kaba4cow.difuse.core.annotation.dependency.Property;
import com.kaba4cow.difuse.core.test.DifuseTestExtension;
import com.kaba4cow.difuse.core.test.annotation.TestContext;

@DifuseContext
@WithConfigs({ "application" })
@WithProfiles({ "test" })
@TestContext(EnvironmentTest.class)
@ExtendWith(DifuseTestExtension.class)
public class EnvironmentTest {

	@Test
	public void applicationNameIsDifuseTests(@Property("application.name") String applicationName) {
		assertEquals("DifuseTests", applicationName);
	}

	@Test
	public void testValueIsPresent(@Property("test.value") String testValue) {
		assertEquals("Present", testValue);
	}

}
