package com.kaba4cow.difuse.core.protection;

import com.kaba4cow.difuse.core.annotation.bean.Bean;
import com.kaba4cow.difuse.core.annotation.dependency.NonRequired;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.protection.ProtectedBeans.AccessibleBean;
import com.kaba4cow.difuse.core.protection.ProtectedBeans.NonAccessibleBean;

@Bean
public class AccessorBean {

	@Provided
	public AccessibleBean accessibleBean;

	@NonRequired
	@Provided
	public NonAccessibleBean nonAccessibleBean;

}
