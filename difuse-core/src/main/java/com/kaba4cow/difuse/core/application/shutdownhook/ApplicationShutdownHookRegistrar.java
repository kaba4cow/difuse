package com.kaba4cow.difuse.core.application.shutdownhook;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import com.kaba4cow.difuse.core.annotation.application.ShutdownHook;

public class ApplicationShutdownHookRegistrar {

	private final ApplicationShutdownHookRegistry applicationShutdownHookRegistry;

	public ApplicationShutdownHookRegistrar(ApplicationShutdownHookRegistry applicationShutdownHookRegistry) {
		this.applicationShutdownHookRegistry = applicationShutdownHookRegistry;
	}

	public void registerShutdownHooks(Object component) {
		Set<AutoCloseable> closeables = findShutdownHooks(component);
		if (!closeables.isEmpty())
			applicationShutdownHookRegistry.registerShutdownHooks(component, closeables);
	}

	private Set<AutoCloseable> findShutdownHooks(Object component) {
		return findMethods(component.getClass()).stream()//
				.map(method -> createCloseable(component, method))//
				.collect(Collectors.toSet());
	}

	private AutoCloseable createCloseable(Object component, Method method) {
		return () -> method.invoke(component);
	}

	private Set<Method> findMethods(Class<?> type) {
		return Arrays.stream(type.getDeclaredMethods())//
				.filter(method -> method.isAnnotationPresent(ShutdownHook.class))//
				.collect(Collectors.toSet());
	}

}
