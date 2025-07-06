package com.kaba4cow.difuse.scheduling.bean.postprocessor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.BiFunction;

import com.kaba4cow.difuse.core.bean.BeanLifecyclePhase;
import com.kaba4cow.difuse.core.bean.processor.post.BeanPostProcessor;
import com.kaba4cow.difuse.core.bean.processor.post.impl.BeanPostProcessorReflections;
import com.kaba4cow.difuse.core.bean.provider.impl.ClassBeanProvider;
import com.kaba4cow.difuse.core.bean.source.impl.ClassBeanSource;
import com.kaba4cow.difuse.core.dependency.provider.DependencyProviderSession;
import com.kaba4cow.difuse.scheduling.annotation.task.CronScheduled;
import com.kaba4cow.difuse.scheduling.annotation.task.FixedDelayScheduled;
import com.kaba4cow.difuse.scheduling.annotation.task.FixedRateScheduled;
import com.kaba4cow.difuse.scheduling.failbehavior.FailBehaviorFactory;
import com.kaba4cow.difuse.scheduling.task.MethodTask;
import com.kaba4cow.difuse.scheduling.task.TaskFactory;

public class ScheduledBeanPostProcessor implements BeanPostProcessor {

	private final ScheduledExecutorService scheduler = Executors
			.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());

	@Override
	public BeanLifecyclePhase getLifecyclePhase() {
		return BeanLifecyclePhase.RUNTIME;
	}

	@Override
	public Object process(Object bean, ClassBeanProvider beanProvider, DependencyProviderSession session) {
		ScheduledMethodProcessor methodProcessor = new ScheduledMethodProcessor(bean, beanProvider, session);
		methodProcessor.processMethods(CronScheduled.class, TaskFactory::createCronTask);
		methodProcessor.processMethods(FixedDelayScheduled.class, TaskFactory::createFixedDelayTask);
		methodProcessor.processMethods(FixedRateScheduled.class, TaskFactory::createFixedRateTask);
		return bean;
	}

	private class ScheduledMethodProcessor {

		private final Object bean;

		private final ClassBeanSource beanSource;

		private final DependencyProviderSession session;

		private ScheduledMethodProcessor(Object bean, ClassBeanProvider beanProvider, DependencyProviderSession session) {
			this.bean = bean;
			this.beanSource = beanProvider.getBeanSource();
			this.session = session;
		}

		private <T extends Annotation> void processMethods(//
				Class<T> annotationType, //
				BiFunction<ScheduledExecutorService, MethodTask<T>, Runnable> taskSupplier) {
			BeanPostProcessorReflections.findAnnotatedMethods(beanSource, annotationType).stream()//
					.map(method -> createMethodTask(annotationType, method))//
					.map(task -> taskSupplier.apply(scheduler, task))//
					.forEach(Runnable::run);
		}

		private <T extends Annotation> MethodTask<T> createMethodTask(//
				Class<T> annotationType, //
				Method method) {
			return new MethodTask<>(//
					method.getAnnotation(annotationType), //
					() -> method.invoke(bean, session.provideDependencies(method)), //
					FailBehaviorFactory.createFailBehavior(method)//
			);
		}

	}

}
