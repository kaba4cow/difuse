package com.kaba4cow.difuse.core.system.shutdownhook;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.util.LoggingTimer;

public class SystemShutdownHookDispatcher extends Thread {

	private static final Logger log = LoggerFactory.getLogger("SystemShutdownHookDispatcher");

	private final SystemShutdownHookRegistry registry;

	public SystemShutdownHookDispatcher(SystemShutdownHookRegistry registry) {
		super("SystemShutdownHookDispatcher");
		this.registry = registry;
	}

	@Override
	public void run() {
		try (LoggingTimer timer = new LoggingTimer(log, "Dispatching shutdown hooks...")) {
			for (Map.Entry<Object, Set<AutoCloseable>> entry : registry.getAllShutdownHooks()) {
				Object component = entry.getKey();
				Set<AutoCloseable> closeables = entry.getValue();
				for (AutoCloseable closeable : closeables)
					try {
						closeable.close();
					} catch (Exception exception) {
						log.error("Could not dispatch shutdown hook on {}", component.getClass());
					}
			}
		}
	}

}
