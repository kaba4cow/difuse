package com.kaba4cow.difuse.scheduling.task;

import java.util.concurrent.ScheduledExecutorService;

public abstract class AbstractTask implements Runnable {

	private final MethodTask<?> methodTask;

	private final ScheduledExecutorService scheduler;

	public AbstractTask(MethodTask<?> methodTask, ScheduledExecutorService scheduler) {
		this.methodTask = methodTask;
		this.scheduler = scheduler;
	}

	protected MethodTask<?> getMethodTask() {
		return methodTask;
	}

	protected ScheduledExecutorService getScheduler() {
		return scheduler;
	}

}
