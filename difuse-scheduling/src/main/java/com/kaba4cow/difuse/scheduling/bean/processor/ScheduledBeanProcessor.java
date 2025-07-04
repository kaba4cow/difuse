package com.kaba4cow.difuse.scheduling.bean.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.BiFunction;

import com.kaba4cow.difuse.core.bean.BeanLifecyclePhase;
import com.kaba4cow.difuse.core.bean.processor.BeanProcessor;
import com.kaba4cow.difuse.core.bean.processor.impl.BeanProcessorReflections;
import com.kaba4cow.difuse.core.bean.provider.BeanProvider;
import com.kaba4cow.difuse.core.bean.source.BeanSource;
import com.kaba4cow.difuse.core.dependency.provider.DependencyProviderSession;
import com.kaba4cow.difuse.scheduling.annotation.task.CronScheduled;
import com.kaba4cow.difuse.scheduling.annotation.task.FixedDelayScheduled;
import com.kaba4cow.difuse.scheduling.annotation.task.FixedRateScheduled;
import com.kaba4cow.difuse.scheduling.failbehavior.FailBehaviorFactory;
import com.kaba4cow.difuse.scheduling.task.MethodTask;
import com.kaba4cow.difuse.scheduling.task.TaskFactory;

public class ScheduledBeanProcessor implements BeanProcessor {

	private final ScheduledExecutorService scheduler = Executors
			.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());

	@Override
	public BeanLifecyclePhase getLifecyclePhase() {
		return BeanLifecyclePhase.RUNTIME;
	}

	@Override
	public Object process(Object bean, BeanProvider<?> beanProvider, DependencyProviderSession session) {
		BeanSource<?> beanSource = beanProvider.getBeanSource();
		processScheduledMethods(bean, beanSource, session, CronScheduled.class, TaskFactory::createCronTask);
		processScheduledMethods(bean, beanSource, session, FixedDelayScheduled.class, TaskFactory::createFixedDelayTask);
		processScheduledMethods(bean, beanSource, session, FixedRateScheduled.class, TaskFactory::createFixedRateTask);
		return bean;
	}

	private <T extends Annotation> void processScheduledMethods(//
			Object bean, //
			BeanSource<?> beanSource, //
			DependencyProviderSession session, //
			Class<T> annotationType, //
			BiFunction<ScheduledExecutorService, MethodTask<T>, Runnable> taskSupplier) {
		BeanProcessorReflections.findAnnotatedMethods(beanSource, annotationType).stream()//
				.map(method -> createMethodTask(annotationType, method, bean, session))//
				.map(methodTask -> taskSupplier.apply(scheduler, methodTask))//
				.forEach(Runnable::run);
	}

	private <T extends Annotation> MethodTask<T> createMethodTask(//
			Class<T> annotationType, //
			Method method, //
			Object bean, //
			DependencyProviderSession session) {
		return new MethodTask<>(//
				method.getAnnotation(annotationType), //
				() -> method.invoke(bean, session.provideDependencies(method)), //
				FailBehaviorFactory.createFailBehavior(method)//
		);
	}

}
