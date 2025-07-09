package com.kaba4cow.difuse.core.environment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.kaba4cow.difuse.core.annotation.context.DifuseContext;
import com.kaba4cow.difuse.core.annotation.context.WithConfigs;
import com.kaba4cow.difuse.core.annotation.dependency.Property;
import com.kaba4cow.difuse.core.test.DifuseTestExtension;
import com.kaba4cow.difuse.core.test.annotation.TestContext;

@DifuseContext
@WithConfigs({ "readers" })
@TestContext(ReaderTest.class)
@ExtendWith(DifuseTestExtension.class)
public class ReaderTest {

	@Test
	public void propertiesLoaded(@Property("properties") String loaded) {
		assertEquals("Loaded", loaded);
	}

	@Test
	public void yamlLoaded(@Property("yaml") String loaded) {
		assertEquals("Loaded", loaded);
	}

	@Test
	public void iniLoaded(@Property("ini") String loaded) {
		assertEquals("Loaded", loaded);
	}

}
