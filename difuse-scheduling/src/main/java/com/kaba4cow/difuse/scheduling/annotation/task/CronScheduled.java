package com.kaba4cow.difuse.scheduling.annotation.task;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.cronutils.model.CronType;

@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface CronScheduled {

	String value();

	CronType cronType() default CronType.UNIX;

}
