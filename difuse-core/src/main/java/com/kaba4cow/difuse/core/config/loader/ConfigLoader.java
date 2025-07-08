package com.kaba4cow.difuse.core.config.loader;

import java.io.InputStream;

public interface ConfigLoader {

	InputStream getInputStream(String config);

}
