package com.kaba4cow.difuse.core.type.converter.support.converter;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.kaba4cow.difuse.core.type.TypeDescriptor;
import com.kaba4cow.difuse.core.type.converter.TypeConverterException;
import com.kaba4cow.difuse.core.type.converter.support.GlobalTypeConverter;

public class MapConverter {

	private final GlobalTypeConverter typeConverter;

	public MapConverter(GlobalTypeConverter typeConverter) {
		this.typeConverter = typeConverter;
	}

	public Object convert(Object raw, TypeDescriptor valueType) {
		if (raw instanceof Map<?, ?>)
			return convertFromMap((Map<?, ?>) raw, valueType);
		else
			throw new TypeConverterException(
					String.format("Expected string or map for Map<String, V>, got %s", raw.getClass().getName()));
	}

	private Map<String, Object> convertFromMap(Map<?, ?> rawMap, TypeDescriptor valueType) {
		Map<String, Object> map = new HashMap<>();
		for (Entry<?, ?> entry : rawMap.entrySet()) {
			Object key = entry.getKey();
			if (key instanceof String) {
				Object value = typeConverter.convert(entry.getValue(), valueType);
				map.put((String) key, value);
			} else
				throw new TypeConverterException(String.format("Expected String as Map key, got %s", key.getClass().getName()));
		}
		return map;
	}

}
