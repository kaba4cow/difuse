package com.kaba4cow.difuse.core.environment.config.reader.support;

import java.lang.reflect.Constructor;

import com.kaba4cow.difuse.core.DifuseException;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.environment.config.reader.ConfigSourceReader;
import com.kaba4cow.difuse.core.util.reflections.ConstructorScanner;

@SystemBean
public class ConfigSourceReaderRegistrar {

	@Provided
	private ConfigSourceReaderRegistry registry;

	public void register(Class<? extends ConfigSourceReader> type) {
		try {
			Constructor<?> constructor = ConstructorScanner.of(type).findNoArgsConstructor();
			Object instance = constructor.newInstance();
			registry.registerReader(type.cast(instance));
		} catch (Exception exception) {
			throw new DifuseException(String.format("Could not instantiate ConfigSourceReader %s", type.getName()), exception);
		}
	}

}
