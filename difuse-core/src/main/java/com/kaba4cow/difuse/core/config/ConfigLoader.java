package com.kaba4cow.difuse.core.config;

import java.io.InputStream;

public interface ConfigLoader {

	InputStream getInputStream(String config) throws Exception;

}
