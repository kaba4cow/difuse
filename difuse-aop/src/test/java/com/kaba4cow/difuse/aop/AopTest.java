package com.kaba4cow.difuse.aop;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.kaba4cow.difuse.core.annotation.context.DifuseContext;
import com.kaba4cow.difuse.core.test.DifuseTestExtension;
import com.kaba4cow.difuse.core.test.annotation.TestContext;

@DifuseContext
@TestContext(AopTest.class)
@ExtendWith(DifuseTestExtension.class)
public class AopTest {

	@Test
	public void everythingInvoked(TestBean bean, AspectFlags flags) {
		bean.annotatedMethod();
		assertTrue(flags.isBeforeInvoked());
		assertTrue(flags.isMethodInvoked());
		assertTrue(flags.isAfterInvoked());
	}

}
