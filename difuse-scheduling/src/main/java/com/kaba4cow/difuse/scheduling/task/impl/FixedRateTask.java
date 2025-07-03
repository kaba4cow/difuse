package com.kaba4cow.difuse.scheduling.task.impl;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.kaba4cow.difuse.scheduling.task.AbstractTask;
import com.kaba4cow.difuse.scheduling.task.MethodTask;

public class FixedRateTask extends AbstractTask {

	private final long rate;

	private final long initialDelay;

	private final TimeUnit timeUnit;

	public FixedRateTask(MethodTask<?> methodTask, ScheduledExecutorService scheduler, long rate, long initialDelay,
			TimeUnit timeUnit) {
		super(methodTask, scheduler);
		this.rate = rate;
		this.initialDelay = initialDelay;
		this.timeUnit = timeUnit;
	}

	@Override
	public void run() {
		ScheduledFuture<?> future = getScheduler().scheduleAtFixedRate(//
				getMethodTask(), //
				initialDelay, //
				rate, //
				timeUnit//
		);
		getMethodTask().setFuture(future);
	}

}
