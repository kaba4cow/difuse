package com.kaba4cow.difuse.core.util;

public class ExecutionTimer {

	private long startTimeMillis;
	private boolean started;

	private long finishTimeMillis;
	private boolean finished;

	public ExecutionTimer() {
		reset();
	}

	public ExecutionTimer start() {
		checkNonStarted();
		startTimeMillis = System.currentTimeMillis();
		started = true;
		return this;
	}

	public ExecutionTimer finish() {
		checkStarted();
		checkNonFinished();
		finishTimeMillis = System.currentTimeMillis();
		finished = true;
		return this;
	}

	public ExecutionTimer reset() {
		startTimeMillis = 0L;
		started = false;
		finishTimeMillis = 0L;
		finished = false;
		return this;
	}

	public long getExecutionMillis() {
		checkStarted();
		checkFinished();
		return finishTimeMillis - startTimeMillis;
	}

	private void checkStarted() {
		if (!started)
			throw new IllegalStateException("Timer has not started yet");
	}

	private void checkFinished() {
		if (!finished)
			throw new IllegalStateException("Timer has not finished yet");
	}

	private void checkNonStarted() {
		if (started)
			throw new IllegalStateException("Timer has already started");
	}

	private void checkNonFinished() {
		if (finished)
			throw new IllegalStateException("Timer has already finished");
	}

}
