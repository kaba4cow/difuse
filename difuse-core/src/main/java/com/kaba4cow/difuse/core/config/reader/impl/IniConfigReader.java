package com.kaba4cow.difuse.core.config.reader.impl;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kaba4cow.difuse.core.config.reader.ConfigReader;
import com.kaba4cow.difuse.core.config.source.ConfigSource;
import com.kaba4cow.difuse.core.config.source.impl.MapConfigSource;
import com.kaba4cow.iniconfig.IniConfig;
import com.kaba4cow.iniconfig.IniReader;
import com.kaba4cow.iniconfig.IniSection;

public class IniConfigReader implements ConfigReader {

	@Override
	public ConfigSource read(String name, InputStream input) throws Exception {
		IniConfig config = IniReader.read(input);
		Map<String, Object> map = new HashMap<>();
		for (String sectionName : config.getSectionNames()) {
			IniSection section = config.getSection(sectionName);
			for (String propertyName : section.getPropertyNames()) {
				String key = buildKey(sectionName, propertyName);
				String value = section.getProperty(propertyName);
				map.put(key, value);
			}
		}
		return new MapConfigSource(name, map);
	}

	private String buildKey(String sectionName, String propertyName) {
		return sectionName.isEmpty()//
				? propertyName//
				: String.format("%s.%s", sectionName, propertyName);
	}

	@Override
	public List<String> getExtensions() {
		return Arrays.asList("ini");
	}

}
