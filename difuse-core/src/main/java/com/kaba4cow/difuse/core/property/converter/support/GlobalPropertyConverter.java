package com.kaba4cow.difuse.core.property.converter.support;

import java.util.Objects;

import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.property.converter.PropertyConverter;
import com.kaba4cow.difuse.core.property.converter.PropertyConverterException;

@SystemBean
public class GlobalPropertyConverter {

	@Provided
	private PropertyConverterRegistry registry;

	public <T> T convert(String raw, Class<T> targetType) {
		PropertyConverter<T> converter = registry.getConverter(targetType);
		if (Objects.isNull(converter))
			throw new PropertyConverterException(String.format("Found no converter for %s", targetType.getName()));
		try {
			return converter.convert(raw);
		} catch (Exception exception) {
			throw new PropertyConverterException(String.format("Could not convert %s", targetType.getName()));
		}
	}

}
