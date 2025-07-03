package com.kaba4cow.difuse.core.injection.scoped.thread;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.kaba4cow.difuse.core.annotation.context.DifuseContext;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.test.DifuseTestExtension;
import com.kaba4cow.difuse.core.test.annotation.SeparateThread;
import com.kaba4cow.difuse.core.test.annotation.TestContext;

@DifuseContext
@TestContext(ThreadScopeTest.class)
@ExtendWith(DifuseTestExtension.class)
public class ThreadScopeTest {

	@Provided
	private ThreadBean currentThreadBean;

	@Provided
	@SeparateThread
	private ThreadBean separateThreadBean;

	@Test
	public void beanNotNull(ThreadBean bean) {
		assertNotNull(bean);
	}

	@Test
	public void beansInOneThreadEqual(ThreadBean firstBean, ThreadBean secondBean) {
		assertEquals(firstBean, secondBean);
	}

	@Test
	public void beansInSeparateThreadsNotEqual() {
		assertNotEquals(currentThreadBean, separateThreadBean);
	}

}
