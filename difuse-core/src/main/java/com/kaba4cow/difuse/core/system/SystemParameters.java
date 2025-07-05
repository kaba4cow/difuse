package com.kaba4cow.difuse.core.system;

import java.util.Optional;

public class SystemParameters {

	private final Class<?> sourceClass;

	private final String[] commandLineArgs;

	private final Class<?> testClass;

	public SystemParameters(Class<?> sourceClass, String[] commandLineArgs, Class<?> testClass) {
		this.sourceClass = sourceClass;
		this.commandLineArgs = commandLineArgs;
		this.testClass = testClass;
	}

	public Class<?> getSourceClass() {
		return sourceClass;
	}

	public String[] getCommandLineArgs() {
		return commandLineArgs;
	}

	public Optional<Class<?>> getTestClass() {
		return Optional.ofNullable(testClass);
	}

}
