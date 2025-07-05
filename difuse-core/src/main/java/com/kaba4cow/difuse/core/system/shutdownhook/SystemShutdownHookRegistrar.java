package com.kaba4cow.difuse.core.system.shutdownhook;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import com.kaba4cow.difuse.core.annotation.system.SystemShutdownHook;

public class SystemShutdownHookRegistrar {

	private final SystemShutdownHookRegistry shutdownHookRegistry;

	public SystemShutdownHookRegistrar(SystemShutdownHookRegistry shutdownHookRegistry) {
		this.shutdownHookRegistry = shutdownHookRegistry;
	}

	public void registerShutdownHooks(Object component) {
		Set<AutoCloseable> closeables = findShutdownHooks(component);
		if (!closeables.isEmpty())
			shutdownHookRegistry.registerShutdownHooks(component, closeables);
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
				.filter(method -> method.isAnnotationPresent(SystemShutdownHook.class))//
				.collect(Collectors.toSet());
	}

}
