package com.kaba4cow.difuse.conditions;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.kaba4cow.difuse.core.annotation.context.DifuseContext;

@Documented
@Retention(RUNTIME)
@Target(TYPE)
@DifuseContext
public @interface EnableConditions {

}
