package com.kaba4cow.difuse.core.config.reader.impl;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.kaba4cow.difuse.core.config.reader.ConfigReader;
import com.kaba4cow.difuse.core.config.source.ConfigSource;
import com.kaba4cow.difuse.core.config.source.impl.MapConfigSource;

public class PropertiesConfigReader implements ConfigReader {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ConfigSource read(String name, InputStream input) throws Exception {
		Properties properties = new Properties();
		properties.load(input);
		return new MapConfigSource(name, (Map) properties);
	}

	@Override
	public List<String> getExtensions() {
		return Arrays.asList("properties");
	}

}
