package com.kaba4cow.difuse.aop;

import com.kaba4cow.difuse.aop.annotation.Aspect;
import com.kaba4cow.difuse.aop.annotation.advice.After;
import com.kaba4cow.difuse.aop.annotation.advice.Before;
import com.kaba4cow.difuse.aop.annotation.pointcut.OnAnnotations;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;

@Aspect
public class TestAspect {

	@Provided
	private AspectFlags flags;

	@Before
	@OnAnnotations(AspectTarget.class)
	public void before() {
		flags.beforeInvoked();
	}

	@After
	@OnAnnotations(AspectTarget.class)
	public void after() {
		flags.afterInvoked();
	}

}
