package com.kaba4cow.difuse.conditions.onprofiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.kaba4cow.difuse.conditions.EnableConditions;
import com.kaba4cow.difuse.conditions.disabled.DisabledBean;
import com.kaba4cow.difuse.conditions.onprofiles.OnProfilesBeans.DevProfileBean;
import com.kaba4cow.difuse.conditions.onprofiles.OnProfilesBeans.ProfileBean;
import com.kaba4cow.difuse.conditions.onprofiles.OnProfilesBeans.TestProfileBean;
import com.kaba4cow.difuse.core.annotation.context.DifuseContext;
import com.kaba4cow.difuse.core.annotation.context.WithProfiles;
import com.kaba4cow.difuse.core.annotation.dependency.NonRequired;
import com.kaba4cow.difuse.core.test.DifuseTestExtension;
import com.kaba4cow.difuse.core.test.annotation.TestContext;

@DifuseContext
@EnableConditions
@WithProfiles("test")
@TestContext(OnProfilesTest.class)
@ExtendWith(DifuseTestExtension.class)
public class OnProfilesTest {

	@Test
	public void beanProfileEqualsTest(ProfileBean bean) {
		assertEquals("test", bean.getProfile());
	}

	@Test
	public void testBeanNotNull(@NonRequired TestProfileBean bean) {
		assertNotNull(bean);
	}

	@Test
	public void devBeanNull(@NonRequired DevProfileBean bean) {
		assertNull(bean);
	}

	@Test
	public void disabledBeanNull(@NonRequired DisabledBean bean) {
		assertNull(bean);
	}

}
