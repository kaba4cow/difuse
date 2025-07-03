package com.kaba4cow.difuse.core.environment.config.reader.impl;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import com.kaba4cow.difuse.core.environment.config.reader.ConfigSourceReader;
import com.kaba4cow.difuse.core.environment.config.source.ConfigSource;
import com.kaba4cow.difuse.core.environment.config.source.impl.MapConfigSource;

public class PropertiesFileConfigSourceReader implements ConfigSourceReader {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ConfigSource read(String name, InputStream input) throws Exception {
		Properties properties = new Properties();
		properties.load(input);
		return new MapConfigSource(name, (Map) properties);
	}

}
