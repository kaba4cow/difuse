package com.kaba4cow.difuse.conditions.disabled;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.kaba4cow.difuse.conditions.EnableConditions;
import com.kaba4cow.difuse.core.annotation.context.DifuseContext;
import com.kaba4cow.difuse.core.annotation.dependency.NonRequired;
import com.kaba4cow.difuse.core.test.DifuseTestExtension;
import com.kaba4cow.difuse.core.test.annotation.TestContext;

@DifuseContext
@EnableConditions
@TestContext(DisabledTest.class)
@ExtendWith(DifuseTestExtension.class)
public class DisabledTest {

	@Test
	public void disabledBeanNull(@NonRequired DisabledBean bean) {
		assertNull(bean);
	}

}
