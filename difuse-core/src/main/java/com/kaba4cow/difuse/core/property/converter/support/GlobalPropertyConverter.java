package com.kaba4cow.difuse.core.property.converter.support;

import java.lang.reflect.Type;
import java.util.Objects;

import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.property.converter.PropertyConverter;
import com.kaba4cow.difuse.core.property.converter.PropertyConverterException;
import com.kaba4cow.difuse.core.util.PrimitiveTypeWrapper;

@SystemBean
public class GlobalPropertyConverter {

	@Provided
	private PropertyConverterRegistry registry;

	public Object convert(String raw, Type type) {
		Class<?> normalizedType = PrimitiveTypeWrapper.wrapIfPrimitive((Class<?>) type);
		try {
			PropertyConverter<?> converter = registry.getConverter(normalizedType);
			if (Objects.isNull(converter))
				throw new PropertyConverterException(String.format("Found no converter for %s", normalizedType.getName()));
			return converter.convert(raw);
		} catch (Exception exception) {
			throw new PropertyConverterException(String.format("Could not convert %s", normalizedType.getName()));
		}
	}

}
