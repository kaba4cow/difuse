package com.kaba4cow.difuse.core.typeconverter.support.converter;

import java.lang.reflect.Type;
import java.util.Collection;

import com.kaba4cow.difuse.core.typeconverter.TypeConverterException;
import com.kaba4cow.difuse.core.typeconverter.support.GlobalTypeConverter;

public class CollectionConverter {

	private final GlobalTypeConverter propertyConverter;

	public CollectionConverter(GlobalTypeConverter propertyConverter) {
		this.propertyConverter = propertyConverter;
	}

	public Object convert(Object raw, Type itemType, Collection<Object> collection) {
		if (raw instanceof String)
			return convertFromString((String) raw, itemType, collection);
		else if (raw instanceof Collection<?>)
			return convertFromCollection((Collection<?>) raw, itemType, collection);
		else
			throw new TypeConverterException(
					String.format("Expected string or collection for List<T>, got %s", raw.getClass().getName()));
	}

	private Object convertFromString(String rawString, Type itemType, Collection<Object> collection) {
		String[] parts = rawString.split(",");
		for (String part : parts)
			collection.add(propertyConverter.convert(part.trim(), itemType));
		return collection;
	}

	private Object convertFromCollection(Collection<?> rawCollection, Type itemType, Collection<Object> collection) {
		for (Object element : rawCollection)
			collection.add(propertyConverter.convert(element, itemType));
		return collection;
	}

}
