package com.kaba4cow.difuse.scheduling.task.impl;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.kaba4cow.difuse.scheduling.task.AbstractTask;
import com.kaba4cow.difuse.scheduling.task.MethodTask;

public class FixedDelayTask extends AbstractTask {

	private final long delay;

	private final long initialDelay;

	private final TimeUnit timeUnit;

	public FixedDelayTask(MethodTask<?> methodTask, ScheduledExecutorService scheduler, long delay, long initialDelay,
			TimeUnit timeUnit) {
		super(methodTask, scheduler);
		this.delay = delay;
		this.initialDelay = initialDelay;
		this.timeUnit = timeUnit;
	}

	@Override
	public void run() {
		ScheduledFuture<?> future = getScheduler().scheduleWithFixedDelay(//
				getMethodTask(), //
				initialDelay, //
				delay, //
				timeUnit//
		);
		getMethodTask().setFuture(future);
	}

}
