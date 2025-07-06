package com.kaba4cow.difuse.core.annotation.access;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.kaba4cow.difuse.core.bean.access.provider.impl.ContextsBeanAccessProvider;

@Documented
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
@AccessProvider(ContextsBeanAccessProvider.class)
public @interface AllowContexts {

	Class<?>[] value();

}
