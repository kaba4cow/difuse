package com.kaba4cow.difuse.aspects.joinpoint;

import com.kaba4cow.difuse.core.util.MethodSignature;

public class JoinPoint {

	private final Object target;

	private final MethodSignature signature;

	private final Object[] args;

	public JoinPoint(Object target, MethodSignature signature, Object[] args) {
		this.target = target;
		this.signature = signature;
		this.args = args;
	}

	public Object getTarget() {
		return target;
	}

	public MethodSignature getSignature() {
		return signature;
	}

	public Object[] getArgs() {
		return args;
	}

}