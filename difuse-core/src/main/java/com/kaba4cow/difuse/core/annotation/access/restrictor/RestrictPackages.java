package com.kaba4cow.difuse.core.annotation.access.restrictor;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.kaba4cow.difuse.core.bean.access.restrictor.impl.PackagesBeanAccessRestrictor;

@Documented
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
@AccessRestrictor(PackagesBeanAccessRestrictor.class)
public @interface RestrictPackages {

	String[] value();

}
