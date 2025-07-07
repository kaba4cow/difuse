package com.kaba4cow.difuse.core.environment.config.reader.support;

import java.lang.reflect.Constructor;

import com.kaba4cow.difuse.core.DifuseException;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.environment.config.reader.PropertySourceReader;
import com.kaba4cow.difuse.core.util.reflections.ConstructorScanner;

@SystemBean
public class PropertySourceReaderRegistrar {

	@Provided
	private PropertySourceReaderRegistry registry;

	public void register(Class<? extends PropertySourceReader> type) {
		try {
			Constructor<?> constructor = ConstructorScanner.of(type).findNoArgsConstructor();
			Object instance = constructor.newInstance();
			registry.registerReader(type.cast(instance));
		} catch (Exception exception) {
			throw new DifuseException(String.format("Could not instantiate PropertySourceReader %s", type.getName()), exception);
		}
	}

}
