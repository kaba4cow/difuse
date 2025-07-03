package com.kaba4cow.difuse.scheduling.task.impl;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.cronutils.model.Cron;
import com.cronutils.model.time.ExecutionTime;
import com.kaba4cow.difuse.scheduling.task.AbstractTask;
import com.kaba4cow.difuse.scheduling.task.MethodTask;

public class CronTask extends AbstractTask {

	private final ExecutionTime executionTime;

	public CronTask(MethodTask<?> methodTask, ScheduledExecutorService scheduler, Cron cron) {
		super(methodTask, scheduler);
		this.executionTime = ExecutionTime.forCron(cron);
	}

	@Override
	public void run() {
		getMethodTask().run();
		ZonedDateTime now = ZonedDateTime.now();
		executionTime.nextExecution(now)//
				.map(next -> Duration.between(now, next))//
				.ifPresent(this::reschedule);
	}

	private void reschedule(Duration duration) {
		long delayMillis = duration.toMillis();
		ScheduledFuture<?> scheduledFuture = getScheduler().schedule(this, delayMillis, TimeUnit.MILLISECONDS);
		getMethodTask().setFuture(scheduledFuture);
	}

}
