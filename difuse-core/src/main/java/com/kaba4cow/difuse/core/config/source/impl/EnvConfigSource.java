package com.kaba4cow.difuse.core.config.source.impl;

import java.util.Map;

import com.kaba4cow.difuse.core.config.source.ConfigSource;

public class EnvConfigSource extends ConfigSource {

	private final Map<String, String> map;

	public EnvConfigSource(String name) {
		super(name);
		this.map = System.getenv();
	}

	@Override
	public Object getProperty(String key) {
		return map.get(transformKey(key));
	}

	@Override
	public boolean hasProperty(String key) {
		return map.containsKey(transformKey(key));
	}

	private String transformKey(String key) {
		return key.toUpperCase().replace('.', '_');
	}

}
