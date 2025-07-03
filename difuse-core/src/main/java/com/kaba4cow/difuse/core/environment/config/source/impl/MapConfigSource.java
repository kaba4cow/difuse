package com.kaba4cow.difuse.core.environment.config.source.impl;

import java.util.Map;

import com.kaba4cow.difuse.core.environment.config.source.ConfigSource;

public class MapConfigSource extends ConfigSource {

	private final Map<String, String> map;

	public MapConfigSource(String name, Map<String, String> map) {
		super(name);
		this.map = map;
	}

	@Override
	public String getProperty(String key) {
		return map.get(key);
	}

}
