package com.kaba4cow.difuse.core.injection.multiple.plain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.kaba4cow.difuse.core.TestUtil;
import com.kaba4cow.difuse.core.annotation.context.DifuseContext;
import com.kaba4cow.difuse.core.injection.multiple.plain.PlainBeans.PlainBean;
import com.kaba4cow.difuse.core.injection.multiple.plain.PlainBeans.PlainBeanImplA;
import com.kaba4cow.difuse.core.injection.multiple.plain.PlainBeans.PlainBeanImplB;
import com.kaba4cow.difuse.core.test.DifuseTestExtension;
import com.kaba4cow.difuse.core.test.annotation.AssertThrows;
import com.kaba4cow.difuse.core.test.annotation.TestContext;

@DifuseContext
@TestContext(PlainBeanInjectionTest.class)
@ExtendWith(DifuseTestExtension.class)
public class PlainBeanInjectionTest {

	@Test
	@AssertThrows(RuntimeException.class)
	public void nonSpecifiedThrows(PlainBean nonSpecified) {}

	@Test
	public void specifiedNotNull(PlainBeanImplA specified) {
		assertNotNull(specified);
	}

	@Test
	public void listNotNull(List<PlainBean> list) {
		assertNotNull(list);
	}

	@Test
	public void listNotEmpty(List<PlainBean> list) {
		assertFalse(list.isEmpty());
	}

	@Test
	public void listContainsRightValues(List<PlainBean> list) {
		List<Class<?>> types = TestUtil.mapObjectsToTypes(list);
		assertTrue(types.contains(PlainBeanImplA.class));
		assertTrue(types.contains(PlainBeanImplB.class));
	}

	@Test
	public void arrayNotNull(PlainBean[] array) {
		assertNotNull(array);
	}

	@Test
	public void arrayNotEmpty(PlainBean[] array) {
		assertNotEquals(0, array.length);
	}

	@Test
	public void arrayContainsRightValues(PlainBean[] array) {
		List<Class<?>> types = TestUtil.mapObjectsToTypes(Arrays.asList(array));
		assertTrue(types.contains(PlainBeanImplA.class));
		assertTrue(types.contains(PlainBeanImplB.class));
	}

	@Test
	@AssertThrows(RuntimeException.class)
	public void mapThrows(Map<String, PlainBean> map) {}

}
