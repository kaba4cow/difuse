package com.kaba4cow.difuse.core.environment.config.reader;

import java.io.InputStream;

import com.kaba4cow.difuse.core.environment.config.source.ConfigSource;

public interface ConfigSourceReader {

	ConfigSource read(String name, InputStream input) throws Exception;

}
