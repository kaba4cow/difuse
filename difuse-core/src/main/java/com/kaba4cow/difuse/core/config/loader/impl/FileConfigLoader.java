package com.kaba4cow.difuse.core.config.loader.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.kaba4cow.difuse.core.config.loader.ConfigLoader;

public class FileConfigLoader implements ConfigLoader {

	@Override
	public InputStream getInputStream(String config) {
		try {
			return new FileInputStream(config);
		} catch (FileNotFoundException exception) {
			return null;
		}
	}

}
