package com.kaba4cow.difuse.core.util.reflections;

import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

import com.kaba4cow.difuse.core.util.MethodSignature;

public class MethodScanner {

	private final Class<?> type;

	private MethodScanner(Class<?> type) {
		this.type = type;
	}

	public static MethodScanner of(Class<?> type) {
		return new MethodScanner(type);
	}

	public Set<Method> findMethods() {
		Map<MethodSignature, Method> methods = new LinkedHashMap<>();
		Class<?> currentClass = type;
		while (Objects.nonNull(currentClass) && currentClass != Object.class) {
			for (Method method : currentClass.getDeclaredMethods())
				methods.putIfAbsent(MethodSignature.of(method), method);
			currentClass = currentClass.getSuperclass();
		}
		for (Class<?> interfaceClass : findInterfaces())
			for (Method method : interfaceClass.getMethods())
				methods.putIfAbsent(MethodSignature.of(method), method);
		return new HashSet<>(methods.values());
	}

	private Set<Class<?>> findInterfaces() {
		Set<Class<?>> interfaces = new HashSet<>();
		Queue<Class<?>> queue = new ArrayDeque<>();
		queue.add(type);
		while (!queue.isEmpty()) {
			Class<?> currentClass = queue.poll();
			for (Class<?> interfaceClass : currentClass.getInterfaces())
				if (interfaces.add(interfaceClass))
					queue.add(interfaceClass);
			Class<?> superClass = currentClass.getSuperclass();
			if (Objects.nonNull(superClass))
				queue.add(superClass);
		}
		return interfaces;
	}

}
