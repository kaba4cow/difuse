package com.kaba4cow.difuse.core.injection.multiple.named;

import com.kaba4cow.difuse.core.annotation.bean.Bean;
import com.kaba4cow.difuse.core.annotation.bean.Named;

public class NamedBeans {

	public static interface NamedBean {}

	@Named("A")
	@Bean
	public static class NamedBeanImplA implements NamedBean {}

	@Named("B")
	@Bean
	public static class NamedBeanImplB implements NamedBean {}

}
