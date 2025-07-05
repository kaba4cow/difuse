package com.kaba4cow.difuse.core.injection.system;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.kaba4cow.difuse.core.annotation.context.DifuseContext;
import com.kaba4cow.difuse.core.bean.protector.BeanProtectorFactory;
import com.kaba4cow.difuse.core.environment.Environment;
import com.kaba4cow.difuse.core.test.DifuseTestExtension;
import com.kaba4cow.difuse.core.test.annotation.AssertThrows;
import com.kaba4cow.difuse.core.test.annotation.TestContext;

@DifuseContext
@TestContext(SystemBeanInjectionTest.class)
@ExtendWith(DifuseTestExtension.class)
public class SystemBeanInjectionTest {

	@Test
	public void accessibleBeanNotNull(Environment accessibleBean) {
		assertNotNull(accessibleBean);
	}

	@Test
	@AssertThrows(RuntimeException.class)
	public void internalBeanThrows(BeanProtectorFactory internalBean) {}

}
