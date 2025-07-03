package com.kaba4cow.difuse.core.injection.source.methods;

import com.kaba4cow.difuse.core.annotation.bean.Bean;

@Bean
public class MethodBeanFactory {

	@Bean
	public MethodBean createMethodBean() {
		return new MethodBean();
	}

}
