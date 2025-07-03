package com.kaba4cow.difuse.core.application.component;

import java.lang.reflect.Constructor;
import java.util.Set;

import com.kaba4cow.difuse.core.DifuseApplication;
import com.kaba4cow.difuse.core.DifuseException;
import com.kaba4cow.difuse.core.annotation.application.CoreComponent;
import com.kaba4cow.difuse.core.application.shutdownhook.ApplicationShutdownHookRegistrar;
import com.kaba4cow.difuse.core.util.reflections.PackageScanner;

public class CoreComponentRegistrar {

	private final CoreComponentRegistry coreComponentRegistry;

	private final ApplicationShutdownHookRegistrar coreShutdownHookRegistrar;

	public CoreComponentRegistrar(CoreComponentRegistry coreComponentRegistry,
			ApplicationShutdownHookRegistrar coreShutdownHookRegistrar) {
		this.coreComponentRegistry = coreComponentRegistry;
		this.coreShutdownHookRegistrar = coreShutdownHookRegistrar;
	}

	public void registerComponents() {
		Set<Class<?>> componentClasses = PackageScanner.of(DifuseApplication.class)
				.searchClassesAnnotatedWith(CoreComponent.class);
		for (Class<?> componentClass : componentClasses)
			registerComponent(componentClass);
	}

	public <T> T registerComponent(Class<T> type) {
		return registerComponent(createInstance(type));
	}

	public <T> T registerComponent(T component) {
		coreComponentRegistry.registerComponent(component);
		coreShutdownHookRegistrar.registerShutdownHooks(component);
		return component;
	}

	private <T> T createInstance(Class<T> type) {
		try {
			Constructor<?> constructor = type.getConstructor();
			T instance = type.cast(constructor.newInstance());
			return instance;
		} catch (Exception exception) {
			throw new DifuseException(String.format("Could not create CoreComponent %s", type), exception);
		}
	}

}
