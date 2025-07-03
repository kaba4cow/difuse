package com.kaba4cow.difuse.scheduling.task;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledFuture;

import com.kaba4cow.difuse.scheduling.failbehavior.FailBehavior;

public class MethodTask<T extends Annotation> implements Runnable {

	private final T annotation;

	private final Callable<?> caller;

	private final FailBehavior failBehavior;

	private ScheduledFuture<?> future;

	public MethodTask(T annotation, Callable<?> caller, FailBehavior failBehavior) {
		this.annotation = annotation;
		this.caller = caller;
		this.failBehavior = failBehavior;
	}

	@Override
	public void run() {
		try {
			caller.call();
		} catch (Throwable throwable) {
			if (Objects.nonNull(future)) {
				Throwable actual = getActualThrowable(throwable);
				if (failBehavior.mustCancel(actual))
					future.cancel(false);
			}
		}
	}

	private Throwable getActualThrowable(Throwable throwable) {
		return throwable instanceof InvocationTargetException//
				? throwable.getCause()//
				: throwable;
	}

	public void setFuture(ScheduledFuture<?> future) {
		this.future = future;
	}

	public T getAnnotation() {
		return annotation;
	}

}
