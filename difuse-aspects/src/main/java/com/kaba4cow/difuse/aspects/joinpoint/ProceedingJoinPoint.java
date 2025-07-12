package com.kaba4cow.difuse.aspects.joinpoint;

import java.lang.reflect.Method;

import com.kaba4cow.difuse.core.util.MethodSignature;

public class ProceedingJoinPoint extends JoinPoint {

	private final Method method;

	public ProceedingJoinPoint(Object target, Method method, Object[] args) {
		super(target, MethodSignature.of(method), args);
		this.method = method;
	}

	public Object proceed() throws Throwable {
		return method.invoke(getTarget(), getArgs());
	}

}