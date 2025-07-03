package com.kaba4cow.difuse.core.injection.multiple.named;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.kaba4cow.difuse.core.TestUtil;
import com.kaba4cow.difuse.core.annotation.bean.Named;
import com.kaba4cow.difuse.core.annotation.context.DifuseContext;
import com.kaba4cow.difuse.core.injection.multiple.named.NamedBeans.NamedBean;
import com.kaba4cow.difuse.core.injection.multiple.named.NamedBeans.NamedBeanImplA;
import com.kaba4cow.difuse.core.injection.multiple.named.NamedBeans.NamedBeanImplB;
import com.kaba4cow.difuse.core.test.DifuseTestExtension;
import com.kaba4cow.difuse.core.test.annotation.AssertThrows;
import com.kaba4cow.difuse.core.test.annotation.TestContext;

@DifuseContext
@TestContext(NamedBeanInjectionTest.class)
@ExtendWith(DifuseTestExtension.class)
public class NamedBeanInjectionTest {

	@Test
	@AssertThrows(RuntimeException.class)
	public void unnamedThrows(NamedBean bean) {}

	@Test
	public void namedNotNull(@Named("A") NamedBean bean) {
		assertNotNull(bean);
	}

	@Test
	public void listNotNull(List<NamedBean> list) {
		assertNotNull(list);
	}

	@Test
	public void listNotEmpty(List<NamedBean> list) {
		assertFalse(list.isEmpty());
	}

	@Test
	public void listContainsRightValues(List<NamedBean> list) {
		List<Class<?>> types = TestUtil.mapObjectsToTypes(list);
		assertTrue(types.contains(NamedBeanImplA.class));
		assertTrue(types.contains(NamedBeanImplB.class));
	}

	@Test
	public void mapNotNull(Map<String, NamedBean> map) {
		assertNotNull(map);
	}

	@Test
	public void mapNotEmpty(Map<String, NamedBean> map) {
		assertFalse(map.isEmpty());
	}

	@Test
	public void mapContainsRightKeys(Map<String, NamedBean> map) {
		assertTrue(map.containsKey("A"));
		assertTrue(map.containsKey("B"));
	}

	@Test
	public void mapContainsRightValues(Map<String, NamedBean> map) {
		List<Class<?>> types = TestUtil.mapObjectsToTypes(map.values());
		assertTrue(types.contains(NamedBeanImplA.class));
		assertTrue(types.contains(NamedBeanImplB.class));
	}

}
