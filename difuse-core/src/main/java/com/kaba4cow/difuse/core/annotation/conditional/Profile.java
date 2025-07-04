package com.kaba4cow.difuse.core.annotation.conditional;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.kaba4cow.difuse.core.bean.source.condition.impl.ProfileBeanSourceCondition;

@Documented
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
@Conditional(ProfileBeanSourceCondition.class)
public @interface Profile {

	String[] value();

}
