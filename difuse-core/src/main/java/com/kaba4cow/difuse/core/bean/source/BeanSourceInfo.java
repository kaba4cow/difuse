package com.kaba4cow.difuse.core.bean.source;

import java.lang.reflect.AnnotatedElement;

import com.kaba4cow.difuse.core.annotation.bean.Lazy;
import com.kaba4cow.difuse.core.annotation.bean.Named;

public class BeanSourceInfo {

	private final String name;

	private final boolean lazy;

	public BeanSourceInfo(BeanSource<?> beanSource) {
		AnnotatedElement element = beanSource.getSourceElement();
		this.name = element.isAnnotationPresent(Named.class)//
				? element.getAnnotation(Named.class).value()//
				: "";
		this.lazy = element.isAnnotationPresent(Lazy.class);
	}

	public String getName() {
		return name;
	}

	public boolean isLazy() {
		return lazy;
	}

	public boolean isEager() {
		return !lazy;
	}

	@Override
	public String toString() {
		return String.format("BeanSourceInfo [name=%s, lazy=%s]", name, lazy);
	}

}
