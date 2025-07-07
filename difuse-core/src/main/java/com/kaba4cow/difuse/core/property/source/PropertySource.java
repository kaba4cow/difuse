package com.kaba4cow.difuse.core.property.source;

public abstract class PropertySource {

	private final String name;

	public PropertySource(String name) {
		this.name = name;
	}

	public abstract String getProperty(String key);

	public abstract boolean hasProperty(String key);

	public String getName() {
		return name;
	}

}
