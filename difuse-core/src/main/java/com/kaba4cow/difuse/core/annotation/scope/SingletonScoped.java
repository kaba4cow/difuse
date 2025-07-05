package com.kaba4cow.difuse.core.annotation.scope;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.kaba4cow.difuse.core.scope.handler.impl.SingletonScope;

@Documented
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
@Scoped(SingletonScope.class)
public @interface SingletonScoped {

}
