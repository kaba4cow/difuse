package com.kaba4cow.difuse.core.property.source.impl;

import java.util.Map;

import com.kaba4cow.difuse.core.property.source.PropertySource;

public class MapPropertySource extends PropertySource {

	private final Map<String, Object> map;

	public MapPropertySource(String name, Map<String, Object> map) {
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
