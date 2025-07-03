package com.kaba4cow.difuse.core.injection.scoped.singleton;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.kaba4cow.difuse.core.annotation.context.DifuseContext;
import com.kaba4cow.difuse.core.test.DifuseTestExtension;
import com.kaba4cow.difuse.core.test.annotation.TestContext;

@DifuseContext
@TestContext(SingletonScopeTest.class)
@ExtendWith(DifuseTestExtension.class)
public class SingletonScopeTest {

	@Test
	public void beanNotNull(SingletonBean bean) {
		assertNotNull(bean);
	}

	@Test
	public void beansEqual(SingletonBean firstBean, SingletonBean secondBean) {
		assertEquals(firstBean, secondBean);
	}

}
