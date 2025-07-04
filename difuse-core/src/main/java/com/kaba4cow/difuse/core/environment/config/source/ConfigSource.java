package com.kaba4cow.difuse.core.environment.config.source;

import java.util.Objects;

public abstract class ConfigSource {

	private final String name;

	public ConfigSource(String name) {
		this.name = name;
	}

	public abstract String getProperty(String key);

	public boolean hasProperty(String key) {
		return Objects.nonNull(getProperty(key));
	}

	public String getName() {
		return name;
	}

}
