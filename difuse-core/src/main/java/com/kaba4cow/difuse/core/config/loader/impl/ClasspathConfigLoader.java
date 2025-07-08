package com.kaba4cow.difuse.core.config.loader.impl;

import java.io.InputStream;

import com.kaba4cow.difuse.core.config.loader.ConfigLoader;

public class ClasspathConfigLoader implements ConfigLoader {

	@Override
	public InputStream getInputStream(String config) {
		return getClass().getClassLoader().getResourceAsStream(config);
	}

}
