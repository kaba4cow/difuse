package com.kaba4cow.difuse.core.annotation.access;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.kaba4cow.difuse.core.bean.access.provider.BeanAccessProvider;

@Documented
@Retention(RUNTIME)
@Target(ANNOTATION_TYPE)
public @interface AccessProvider {

	Class<? extends BeanAccessProvider<?>> value();

}
