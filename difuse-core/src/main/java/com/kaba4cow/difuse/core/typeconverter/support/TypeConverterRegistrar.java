package com.kaba4cow.difuse.core.typeconverter.support;

import java.lang.reflect.Constructor;

import com.kaba4cow.difuse.core.DifuseException;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.typeconverter.TypeConverter;
import com.kaba4cow.difuse.core.util.reflections.ConstructorScanner;

@SystemBean
public class TypeConverterRegistrar {

	@Provided
	private TypeConverterRegistry registry;

	public void register(Class<? extends TypeConverter<?>> type) {
		try {
			Constructor<?> constructor = ConstructorScanner.of(type).findNoArgsConstructor();
			Object instance = constructor.newInstance();
			registry.registerConverter(type.cast(instance));
		} catch (Exception exception) {
			throw new DifuseException(String.format("Could not instantiate TypeConverter %s", type.getName()), exception);
		}
	}

}
