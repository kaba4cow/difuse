package com.kaba4cow.difuse.core.application.shutdownhook;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationShutdownHookRegistry {

	private static final Logger log = LoggerFactory.getLogger("ApplicationShutdownHookRegistry");

	private final Map<Object, Set<AutoCloseable>> registry = new HashMap<>();

	public ApplicationShutdownHookRegistry() {}

	public void registerShutdownHooks(Object component, Set<AutoCloseable> shutdownHooks) {
		registry.put(component, shutdownHooks);
		log.debug("Registered {} shutdown hooks for {}", shutdownHooks.size(), component.getClass());
	}

	public Set<Map.Entry<Object, Set<AutoCloseable>>> getAllShutdownHooks() {
		return Collections.unmodifiableSet(registry.entrySet());
	}

}
