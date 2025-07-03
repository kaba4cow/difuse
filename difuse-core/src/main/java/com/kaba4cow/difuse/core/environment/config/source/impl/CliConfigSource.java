package com.kaba4cow.difuse.core.environment.config.source.impl;

import java.util.Map;
import java.util.TreeMap;

import com.kaba4cow.difuse.core.environment.config.source.ConfigSource;

public class CliConfigSource extends ConfigSource {

	private final Map<String, String> map = new TreeMap<>();

	public CliConfigSource(String name, String[] args) {
		super(name);
		for (String arg : args)
			if (arg.startsWith("--")) {
				String[] parts = arg.substring(2).split("=", 2);
				if (parts.length == 2)
					map.put(parts[0], parts[1]);
			}
	}

	@Override
	public String getProperty(String key) {
		return map.get(key);
	}

}
