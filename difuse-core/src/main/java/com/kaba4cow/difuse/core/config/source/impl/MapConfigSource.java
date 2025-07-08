package com.kaba4cow.difuse.core.config.source.impl;

import java.util.Map;

import com.kaba4cow.difuse.core.config.source.ConfigSource;

public class MapConfigSource extends ConfigSource {

	private final Map<String, Object> map;

	public MapConfigSource(String name, Map<String, Object> map) {
		super(name);
		this.map = map;
	}

	@Override
	public Object getProperty(String key) {
		return map.get(key);
	}

	@Override
	public boolean hasProperty(String key) {
		return map.containsKey(key);
	}

}
