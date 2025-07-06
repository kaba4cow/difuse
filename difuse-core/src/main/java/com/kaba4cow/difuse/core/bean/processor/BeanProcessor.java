package com.kaba4cow.difuse.core.bean.processor;

public interface BeanProcessor {

	default boolean isActive() {
		return true;
	}

}
