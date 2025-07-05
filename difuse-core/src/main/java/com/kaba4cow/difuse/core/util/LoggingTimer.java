package com.kaba4cow.difuse.core.util;

import org.slf4j.Logger;

public class LoggingTimer implements AutoCloseable {

	private final Logger log;

	private final long startTimeMillis;

	private long finishTimeMillis;

	private boolean finished;

	public LoggingTimer(Logger log, String message, Object... args) {
		this.log = log;
		this.startTimeMillis = System.currentTimeMillis();
		this.finishTimeMillis = 0L;
		this.finished = false;
		this.log.info(message, args);
	}

	public LoggingTimer(Logger log) {
		this(log, "Started...");
	}

	@Override
	public void close() {
		if (finished)
			throw new IllegalStateException("Timer has already finished");
		finished = true;
		finishTimeMillis = System.currentTimeMillis();
		long elapsedTimeMillis = finishTimeMillis - startTimeMillis;
		log.info("Finished, process took {} ms", elapsedTimeMillis);
	}

}
