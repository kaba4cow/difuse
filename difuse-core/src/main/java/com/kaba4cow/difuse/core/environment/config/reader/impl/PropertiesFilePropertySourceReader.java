package com.kaba4cow.difuse.core.environment.config.reader.impl;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import com.kaba4cow.difuse.core.environment.config.reader.PropertySourceReader;
import com.kaba4cow.difuse.core.environment.config.source.PropertySource;
import com.kaba4cow.difuse.core.environment.config.source.impl.MapPropertySource;

public class PropertiesFilePropertySourceReader implements PropertySourceReader {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PropertySource read(String name, InputStream input) throws Exception {
		Properties properties = new Properties();
		properties.load(input);
		return new MapPropertySource(name, (Map) properties);
	}

	@Override
	public String getSuffix() {
		return "properties";
	}

}
