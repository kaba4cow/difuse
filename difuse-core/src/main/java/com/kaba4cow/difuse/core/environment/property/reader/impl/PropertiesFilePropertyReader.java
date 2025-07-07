package com.kaba4cow.difuse.core.environment.property.reader.impl;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import com.kaba4cow.difuse.core.environment.property.reader.PropertyReader;
import com.kaba4cow.difuse.core.environment.property.source.PropertySource;
import com.kaba4cow.difuse.core.environment.property.source.impl.MapPropertySource;

public class PropertiesFilePropertyReader implements PropertyReader {

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
