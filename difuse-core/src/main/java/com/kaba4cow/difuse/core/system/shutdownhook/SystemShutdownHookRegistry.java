package com.kaba4cow.difuse.core.system.shutdownhook;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemShutdownHookRegistry {

	private static final Logger log = LoggerFactory.getLogger("SystemShutdownHookRegistry");

	private final Map<Object, Set<AutoCloseable>> registry = new HashMap<>();

	public SystemShutdownHookRegistry() {}

	public void registerShutdownHooks(Object component, Set<AutoCloseable> shutdownHooks) {
		registry.put(component, shutdownHooks);
		log.debug("Registered {} shutdown hooks for {}", shutdownHooks.size(), component.getClass().getName());
	}

	public Set<Map.Entry<Object, Set<AutoCloseable>>> getAllShutdownHooks() {
		return Collections.unmodifiableSet(registry.entrySet());
	}

}
