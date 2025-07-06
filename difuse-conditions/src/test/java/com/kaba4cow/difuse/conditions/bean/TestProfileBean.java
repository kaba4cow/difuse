package com.kaba4cow.difuse.conditions.bean;

import com.kaba4cow.difuse.conditions.annotation.Profile;
import com.kaba4cow.difuse.core.annotation.bean.Bean;

@Profile("test")
@Bean
public class TestProfileBean implements ProfileBean {

	@Override
	public String getProfile() {
		return "test";
	}

}
