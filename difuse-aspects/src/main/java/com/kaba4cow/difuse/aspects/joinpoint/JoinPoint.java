package com.kaba4cow.difuse.aspects.joinpoint;

import java.lang.reflect.Method;

import com.kaba4cow.difuse.core.util.MethodSignature;

public class JoinPoint {

	private final Object target;

	private final Method method;

	private final Object[] args;

	private final MethodSignature signature;

	public JoinPoint(Object target, Method method, Object[] args) {
		this.target = target;
		this.method = method;
		this.args = args;
		this.signature = MethodSignature.of(method);
	}

	public Object getTarget() {
		return target;
	}

	public Method getMethod() {
		return method;
	}

	public Object[] getArgs() {
		return args;
	}

	public MethodSignature getSignature() {
		return signature;
	}

}