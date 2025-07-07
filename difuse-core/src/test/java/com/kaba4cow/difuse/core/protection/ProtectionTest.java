package com.kaba4cow.difuse.core.protection;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.kaba4cow.difuse.core.annotation.context.DifuseContext;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.protection.ProtectedBeans.NonAccessibleBean;
import com.kaba4cow.difuse.core.test.DifuseTestExtension;
import com.kaba4cow.difuse.core.test.annotation.TestContext;

@DifuseContext
@TestContext(ProtectionTest.class)
@ExtendWith(DifuseTestExtension.class)
public class ProtectionTest {

	@Provided
	private AccessorBean bean;

	@Test
	public void accessibleBeanNotNull() {
		assertNotNull(bean.accessibleBean);
	}

	@Test
	public void nonAccessibleBeanNull() {
		assertNull(bean.nonAccessibleBean);
	}

	@Test
	public void testInjectedNotNull(NonAccessibleBean bean) {
		assertNotNull(bean);
	}

}
