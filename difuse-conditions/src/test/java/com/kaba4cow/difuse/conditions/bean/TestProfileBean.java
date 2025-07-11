package com.kaba4cow.difuse.conditions.bean;

import com.kaba4cow.difuse.conditions.annotation.OnProfiles;
import com.kaba4cow.difuse.core.annotation.bean.Bean;

@OnProfiles("test")
@Bean
public class TestProfileBean implements ProfileBean {

	@Override
	public String getProfile() {
		return "test";
	}

}
