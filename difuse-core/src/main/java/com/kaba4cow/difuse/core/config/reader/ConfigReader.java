package com.kaba4cow.difuse.core.config.reader;

import java.io.InputStream;

import com.kaba4cow.difuse.core.config.source.ConfigSource;

public interface ConfigReader {

	ConfigSource read(String name, InputStream input) throws Exception;

	String getSuffix();

}
