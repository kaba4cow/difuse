package com.kaba4cow.difuse.aop.joinpoint;

import java.lang.reflect.Method;

public class ProceedingJoinPoint extends JoinPoint {

	public ProceedingJoinPoint(Object target, Method method, Object[] args) {
		super(target, method, args);
	}

	public Object proceed() throws Throwable {
		return getMethod().invoke(getTarget(), getArgs());
	}

}