package com.kaba4cow.difuse.core.property.source.impl;

import java.util.HashMap;
import java.util.Map;

import com.kaba4cow.difuse.core.property.source.PropertySource;

public class CliPropertySource extends PropertySource {

	private final Map<String, String> map = new HashMap<>();

	public CliPropertySource(String name, String[] args) {
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
