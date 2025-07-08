package com.kaba4cow.difuse.core.config.source;

public abstract class ConfigSource {

	private final String name;

	public ConfigSource(String name) {
		this.name = name;
	}

	public abstract Object getProperty(String key);

	public abstract boolean hasProperty(String key);

	public String getName() {
		return name;
	}

}
