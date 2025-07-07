package com.kaba4cow.difuse.core.environment.property.reader;

import java.io.InputStream;

import com.kaba4cow.difuse.core.environment.property.source.PropertySource;

public interface PropertyReader {

	PropertySource read(String name, InputStream input) throws Exception;

	String getSuffix();

}
