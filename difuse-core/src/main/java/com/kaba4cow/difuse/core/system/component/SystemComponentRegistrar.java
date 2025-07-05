package com.kaba4cow.difuse.core.system.component;

import java.lang.reflect.Constructor;
import java.util.Set;

import com.kaba4cow.difuse.core.DifuseApplication;
import com.kaba4cow.difuse.core.DifuseException;
import com.kaba4cow.difuse.core.annotation.system.SystemComponent;
import com.kaba4cow.difuse.core.system.shutdownhook.SystemShutdownHookRegistrar;
import com.kaba4cow.difuse.core.util.reflections.PackageScanner;

public class SystemComponentRegistrar {

	private final SystemComponentRegistry componentRegistry;

	private final SystemShutdownHookRegistrar shutdownHookRegistrar;

	public SystemComponentRegistrar(SystemComponentRegistry componentRegistry,
			SystemShutdownHookRegistrar shutdownHookRegistrar) {
		this.componentRegistry = componentRegistry;
		this.shutdownHookRegistrar = shutdownHookRegistrar;
	}

	public void registerComponents() {
		Set<Class<?>> componentClasses = PackageScanner.of(DifuseApplication.class)
				.searchClassesAnnotatedWith(SystemComponent.class);
		for (Class<?> componentClass : componentClasses)
			registerComponent(componentClass);
	}

	public <T> T registerComponent(Class<T> type) {
		return registerComponent(createInstance(type));
	}

	public <T> T registerComponent(T component) {
		componentRegistry.registerComponent(component);
		shutdownHookRegistrar.registerShutdownHooks(component);
		return component;
	}

	private <T> T createInstance(Class<T> type) {
		try {
			Constructor<?> constructor = type.getConstructor();
			T instance = type.cast(constructor.newInstance());
			return instance;
		} catch (Exception exception) {
			throw new DifuseException(String.format("Could not create SystemComponent %s", type), exception);
		}
	}

}
