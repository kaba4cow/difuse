package com.kaba4cow.difuse.core.config.reader.support;

import java.lang.reflect.Constructor;

import com.kaba4cow.difuse.core.DifuseException;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.config.reader.ConfigReader;
import com.kaba4cow.difuse.core.util.reflections.ConstructorScanner;

@SystemBean
public class ConfigReaderRegistrar {

	@Provided
	private ConfigReaderRegistry registry;

	public void register(Class<? extends ConfigReader> type) {
		try {
			Constructor<?> constructor = ConstructorScanner.of(type).findNoArgsConstructor();
			Object instance = constructor.newInstance();
			registry.registerReader(type.cast(instance));
		} catch (Exception exception) {
			throw new DifuseException(String.format("Could not instantiate ConfigReader %s", type.getName()), exception);
		}
	}

}
