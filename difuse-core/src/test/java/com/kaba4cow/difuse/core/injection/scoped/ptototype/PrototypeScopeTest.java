package com.kaba4cow.difuse.core.injection.scoped.ptototype;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.kaba4cow.difuse.core.annotation.context.DifuseContext;
import com.kaba4cow.difuse.core.test.DifuseTestExtension;
import com.kaba4cow.difuse.core.test.annotation.TestContext;

@DifuseContext
@TestContext(PrototypeScopeTest.class)
@ExtendWith(DifuseTestExtension.class)
public class PrototypeScopeTest {

	@Test
	public void beanNotNull(PrototypeBean bean) {
		assertNotNull(bean);
	}

	@Test
	public void beansNotEqual(PrototypeBean firstBean, PrototypeBean secondBean) {
		assertNotEquals(firstBean, secondBean);
	}

}
