package com.kaba4cow.difuse.core.annotation.conditional;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.kaba4cow.difuse.core.bean.source.condition.BeanSourceCondition;

@Documented
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface Conditional {

	Class<? extends BeanSourceCondition> value();

}
