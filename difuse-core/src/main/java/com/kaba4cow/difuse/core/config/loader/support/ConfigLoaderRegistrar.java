package com.kaba4cow.difuse.core.config.loader.support;

import java.lang.reflect.Constructor;

import com.kaba4cow.difuse.core.DifuseException;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.config.loader.ConfigLoader;
import com.kaba4cow.difuse.core.util.reflections.ConstructorScanner;

@SystemBean
public class ConfigLoaderRegistrar {

	@Provided
	private ConfigLoaderRegistry registry;

	public void register(Class<? extends ConfigLoader> type) {
		try {
			Constructor<?> constructor = ConstructorScanner.of(type).findNoArgsConstructor();
			Object instance = constructor.newInstance();
			registry.registerLoader(type.cast(instance));
		} catch (Exception exception) {
			throw new DifuseException(String.format("Could not instantiate ConfigLoader %s", type.getName()), exception);
		}
	}

}
