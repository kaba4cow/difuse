package com.kaba4cow.difuse.aspects.aspect;

import com.kaba4cow.difuse.aspects.Call;
import com.kaba4cow.difuse.aspects.annotation.Aspect;
import com.kaba4cow.difuse.aspects.annotation.advice.After;
import com.kaba4cow.difuse.aspects.annotation.advice.Around;
import com.kaba4cow.difuse.aspects.annotation.advice.Before;
import com.kaba4cow.difuse.aspects.annotation.filter.OnAnnotations;
import com.kaba4cow.difuse.aspects.bean.CallHistory;
import com.kaba4cow.difuse.aspects.joinpoint.JoinPoint;
import com.kaba4cow.difuse.aspects.joinpoint.ProceedingJoinPoint;
import com.kaba4cow.difuse.aspects.targets.Logged;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;

@Aspect
public class LoggingAspect {

	@Provided
	private CallHistory callHistory;

	@Before
	@OnAnnotations(Logged.class)
	public void before(JoinPoint joinPoint) {
		callHistory.addCall(Call.LOGGED_BEFORE);
	}

	@Around
	@OnAnnotations(Logged.class)
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		callHistory.addCall(Call.LOGGED_AROUND_BEFORE);
		Object result = joinPoint.proceed();
		callHistory.addCall(Call.LOGGED_AROUND_AFTER);
		return result;
	}

	@After
	@OnAnnotations(Logged.class)
	public void after(JoinPoint joinPoint) {
		callHistory.addCall(Call.LOGGED_AFTER);
	}

}
