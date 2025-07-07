package com.kaba4cow.difuse.core.environment.config.source.impl;

import java.util.Map;

import com.kaba4cow.difuse.core.environment.config.source.PropertySource;

public class EnvPropertySource extends PropertySource {

	private final Map<String, String> map;

	public EnvPropertySource(String name) {
		super(name);
		this.map = System.getenv();
	}

	@Override
	public String getProperty(String key) {
		return map.get(transformKey(key));
	}

	private String transformKey(String key) {
		return key.toUpperCase().replace('.', '_');
	}

}
