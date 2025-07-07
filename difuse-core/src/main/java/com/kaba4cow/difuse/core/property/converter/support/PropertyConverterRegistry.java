package com.kaba4cow.difuse.core.property.converter.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.property.converter.PropertyConverter;

@SystemBean
public class PropertyConverterRegistry {

	private static final Logger log = LoggerFactory.getLogger("PropertyConverterRegistry");

	private final Map<Class<?>, PropertyConverter<?>> registry = new ConcurrentHashMap<>();

	void registerConverter(PropertyConverter<?> converter) {
		Class<?> targetType = converter.getTargetType();
		registry.put(targetType, converter);
		log.debug("Registered {} PropertyConverter {}", targetType.getName(), converter.getClass().getName());
	}

	@SuppressWarnings("unchecked")
	public <T> PropertyConverter<T> getConverter(Class<T> targetType) {
		return (PropertyConverter<T>) registry.get(targetType);
	}

}
