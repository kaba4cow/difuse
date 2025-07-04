package com.kaba4cow.difuse.core.test.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface AssertThrows {

	Class<? extends Throwable> value() default Throwable.class;

	String withMessage() default "";

}
