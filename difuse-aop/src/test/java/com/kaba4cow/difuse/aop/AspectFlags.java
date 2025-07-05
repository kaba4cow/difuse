package com.kaba4cow.difuse.aop;

import com.kaba4cow.difuse.core.annotation.bean.Bean;

@Bean
public class AspectFlags {

	private boolean beforeInvoked;

	private boolean methodInvoked;

	private boolean afterInvoked;

	public void reset() {
		beforeInvoked = false;
		methodInvoked = false;
		afterInvoked = false;
	}

	public void beforeInvoked() {
		beforeInvoked = true;
	}

	public void methodInvoked() {
		methodInvoked = true;
	}

	public void afterInvoked() {
		afterInvoked = true;
	}

	public boolean isBeforeInvoked() {
		return beforeInvoked;
	}

	public boolean isMethodInvoked() {
		return methodInvoked;
	}

	public boolean isAfterInvoked() {
		return afterInvoked;
	}

}
