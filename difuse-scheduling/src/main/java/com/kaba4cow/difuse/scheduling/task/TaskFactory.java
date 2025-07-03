package com.kaba4cow.difuse.scheduling.task;

import java.util.concurrent.ScheduledExecutorService;

import com.cronutils.model.Cron;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;
import com.kaba4cow.difuse.scheduling.annotation.task.CronScheduled;
import com.kaba4cow.difuse.scheduling.annotation.task.FixedDelayScheduled;
import com.kaba4cow.difuse.scheduling.annotation.task.FixedRateScheduled;
import com.kaba4cow.difuse.scheduling.task.impl.CronTask;
import com.kaba4cow.difuse.scheduling.task.impl.FixedDelayTask;
import com.kaba4cow.difuse.scheduling.task.impl.FixedRateTask;

public class TaskFactory {

	private TaskFactory() {}

	public static Runnable createFixedDelayTask(ScheduledExecutorService scheduler,
			MethodTask<FixedDelayScheduled> methodTask) {
		FixedDelayScheduled annotation = methodTask.getAnnotation();
		return new FixedDelayTask(//
				methodTask, //
				scheduler, //
				annotation.value(), //
				annotation.initialDelay(), //
				annotation.timeUnit()//
		);
	}

	public static Runnable createFixedRateTask(ScheduledExecutorService scheduler, MethodTask<FixedRateScheduled> methodTask) {
		FixedRateScheduled annotation = methodTask.getAnnotation();
		return new FixedRateTask(//
				methodTask, //
				scheduler, //
				annotation.value(), //
				annotation.initialDelay(), //
				annotation.timeUnit()//
		);
	}

	public static Runnable createCronTask(ScheduledExecutorService scheduler, MethodTask<CronScheduled> methodTask) {
		CronScheduled annotation = methodTask.getAnnotation();
		CronParser parser = new CronParser(CronDefinitionBuilder.instanceDefinitionFor(annotation.cronType()));
		Cron cron = parser.parse(annotation.value());
		return new CronTask(//
				methodTask, //
				scheduler, //
				cron//
		);
	}

}
