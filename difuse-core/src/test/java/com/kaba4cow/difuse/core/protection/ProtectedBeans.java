package com.kaba4cow.difuse.core.protection;

import com.kaba4cow.difuse.core.annotation.access.provider.AllowClasses;
import com.kaba4cow.difuse.core.annotation.bean.Bean;

public class ProtectedBeans {

	@AllowClasses(AccessorBean.class)
	@Bean
	public static class AccessibleBean {}

	@AllowClasses(AccessibleBean.class)
	@Bean
	public static class NonAccessibleBean {}

}
