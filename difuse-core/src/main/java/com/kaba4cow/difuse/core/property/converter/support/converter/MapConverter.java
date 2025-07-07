package com.kaba4cow.difuse.core.property.converter.support.converter;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.kaba4cow.difuse.core.property.converter.PropertyConverterException;
import com.kaba4cow.difuse.core.property.converter.support.GlobalPropertyConverter;

public class MapConverter {

	private final GlobalPropertyConverter propertyConverter;

	public MapConverter(GlobalPropertyConverter propertyConverter) {
		this.propertyConverter = propertyConverter;
	}

	public Object convert(Object raw, Type valueType) {
		if (raw instanceof Map<?, ?>)
			return convertFromMap((Map<?, ?>) raw, valueType);
		else
			throw new PropertyConverterException(
					String.format("Expected string or map for Map<String, V>, got %s", raw.getClass().getName()));
	}

	private Map<String, Object> convertFromMap(Map<?, ?> rawMap, Type valueType) {
		Map<String, Object> map = new HashMap<>();
		for (Map.Entry<?, ?> entry : rawMap.entrySet()) {
			Object key = entry.getKey();
			if (key instanceof String) {
				Object value = propertyConverter.convert(entry.getValue(), valueType);
				map.put((String) key, value);
			} else
				throw new PropertyConverterException(
						String.format("Expected String as Map key, got %s", key.getClass().getName()));
		}
		return map;
	}

}
