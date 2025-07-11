package com.kaba4cow.difuse.conditions.bean;

import com.kaba4cow.difuse.conditions.annotation.OnProfiles;
import com.kaba4cow.difuse.core.annotation.bean.Bean;

@OnProfiles("dev")
@Bean
public class DevProfileBean implements ProfileBean {

	@Override
	public String getProfile() {
		return "dev";
	}

}
