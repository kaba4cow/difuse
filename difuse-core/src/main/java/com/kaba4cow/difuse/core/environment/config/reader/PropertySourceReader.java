package com.kaba4cow.difuse.core.environment.config.reader;

import java.io.InputStream;

import com.kaba4cow.difuse.core.environment.config.source.PropertySource;

public interface PropertySourceReader {

	PropertySource read(String name, InputStream input) throws Exception;

	String getSuffix();

}
