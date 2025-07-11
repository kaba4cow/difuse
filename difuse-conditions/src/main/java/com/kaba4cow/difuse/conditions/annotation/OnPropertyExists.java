package com.kaba4cow.difuse.conditions.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.kaba4cow.difuse.conditions.condition.impl.OnPropertyExistsBeanCondition;

@Documented
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
@Conditional(OnPropertyExistsBeanCondition.class)
public @interface OnPropertyExists {

	String value();

}
