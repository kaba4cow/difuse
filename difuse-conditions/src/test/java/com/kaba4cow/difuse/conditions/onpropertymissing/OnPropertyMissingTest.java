package com.kaba4cow.difuse.conditions.onpropertymissing;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.kaba4cow.difuse.conditions.EnableConditions;
import com.kaba4cow.difuse.conditions.onpropertyequals.OnPropertyEqualsBeans.FailingBean;
import com.kaba4cow.difuse.conditions.onpropertyequals.OnPropertyEqualsBeans.PassingBean;
import com.kaba4cow.difuse.core.annotation.context.DifuseContext;
import com.kaba4cow.difuse.core.annotation.context.WithConfigs;
import com.kaba4cow.difuse.core.annotation.dependency.NonRequired;
import com.kaba4cow.difuse.core.test.DifuseTestExtension;
import com.kaba4cow.difuse.core.test.annotation.TestContext;

@DifuseContext
@EnableConditions
@WithConfigs("test")
@TestContext(OnPropertyMissingTest.class)
@ExtendWith(DifuseTestExtension.class)
public class OnPropertyMissingTest {

	@Test
	public void passingBeanNotNull(PassingBean bean) {
		assertNotNull(bean);
	}

	@Test
	public void failingBeanNull(@NonRequired FailingBean bean) {
		assertNull(bean);
	}

}
