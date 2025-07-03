package com.kaba4cow.difuse.core.injection.multiple.plain;

import com.kaba4cow.difuse.core.annotation.bean.Bean;

public class PlainBeans {

	public static interface PlainBean {}

	@Bean
	public static class PlainBeanImplA implements PlainBean {}

	@Bean
	public static class PlainBeanImplB implements PlainBean {}

}
