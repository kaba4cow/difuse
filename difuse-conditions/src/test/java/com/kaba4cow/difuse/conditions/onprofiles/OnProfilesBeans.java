package com.kaba4cow.difuse.conditions.onprofiles;

import com.kaba4cow.difuse.conditions.annotation.OnProfiles;
import com.kaba4cow.difuse.core.annotation.bean.Bean;

public class OnProfilesBeans {

	public static interface ProfileBean {

		String getProfile();

	}

	@OnProfiles("test")
	@Bean
	public static class TestProfileBean implements ProfileBean {

		@Override
		public String getProfile() {
			return "test";
		}

	}

	@OnProfiles("dev")
	@Bean
	public static class DevProfileBean implements ProfileBean {

		@Override
		public String getProfile() {
			return "dev";
		}

	}

}
