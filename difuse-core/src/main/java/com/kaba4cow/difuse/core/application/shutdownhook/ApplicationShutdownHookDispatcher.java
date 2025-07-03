package com.kaba4cow.difuse.core.application.shutdownhook;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.util.ExecutionTimer;

public class ApplicationShutdownHookDispatcher extends Thread {

	private static final Logger log = LoggerFactory.getLogger("ApplicationShutdownHookDispatcher");

	private final ApplicationShutdownHookRegistry registry;

	public ApplicationShutdownHookDispatcher(ApplicationShutdownHookRegistry registry) {
		super("ApplicationShutdownHookDispatcher");
		this.registry = registry;
	}

	@Override
	public void run() {
		log.info("Dispatching shutdown hooks...");
		ExecutionTimer timer = new ExecutionTimer().start();

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

		log.info("Shutdown hook dispatching took {} ms", timer.finish().getExecutionMillis());
	}

}
