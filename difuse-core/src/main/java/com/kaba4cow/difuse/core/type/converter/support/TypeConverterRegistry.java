package com.kaba4cow.difuse.core.type.converter.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.type.converter.PrimitiveTypeConverter;
import com.kaba4cow.difuse.core.type.converter.TypeConverter;

@SystemBean
public class TypeConverterRegistry {

	private static final Logger log = LoggerFactory.getLogger("TypeConverterRegistry");

	private final Map<Class<?>, TypeConverter<?>> registry = new ConcurrentHashMap<>();

	void registerConverter(TypeConverter<?> converter) {
		Class<?> targetType = converter.getTargetType();
		registry.put(targetType, converter);
		if (converter instanceof PrimitiveTypeConverter) {
			PrimitiveTypeConverter primitiveConverter = (PrimitiveTypeConverter) converter;
			registry.put(primitiveConverter.getPrimitiveTargetType(), converter);
		}
		log.debug("Registered {} TypeConverter {}", targetType.getName(), converter.getClass().getName());
	}

	@SuppressWarnings("unchecked")
	public <T> TypeConverter<T> getConverter(Class<T> targetType) {
		return (TypeConverter<T>) registry.get(targetType);
	}

}
