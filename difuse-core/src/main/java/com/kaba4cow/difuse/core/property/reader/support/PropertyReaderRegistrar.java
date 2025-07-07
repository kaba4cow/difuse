package com.kaba4cow.difuse.core.property.reader.support;

import java.lang.reflect.Constructor;

import com.kaba4cow.difuse.core.DifuseException;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.property.reader.PropertyReader;
import com.kaba4cow.difuse.core.util.reflections.ConstructorScanner;

@SystemBean
public class PropertyReaderRegistrar {

	@Provided
	private PropertyReaderRegistry registry;

	public void register(Class<? extends PropertyReader> type) {
		try {
			Constructor<?> constructor = ConstructorScanner.of(type).findNoArgsConstructor();
			Object instance = constructor.newInstance();
			registry.registerReader(type.cast(instance));
		} catch (Exception exception) {
			throw new DifuseException(String.format("Could not instantiate PropertyReader %s", type.getName()), exception);
		}
	}

}
