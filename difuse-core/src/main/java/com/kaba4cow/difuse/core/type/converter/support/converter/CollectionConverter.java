package com.kaba4cow.difuse.core.type.converter.support.converter;

import java.util.Collection;

import com.kaba4cow.difuse.core.type.TypeDescriptor;
import com.kaba4cow.difuse.core.type.converter.TypeConverterException;
import com.kaba4cow.difuse.core.type.converter.support.GlobalTypeConverter;

public class CollectionConverter {

	private final GlobalTypeConverter typeConverter;

	public CollectionConverter(GlobalTypeConverter typeConverter) {
		this.typeConverter = typeConverter;
	}

	public Object convert(Object raw, TypeDescriptor itemType, Collection<Object> collection) {
		if (raw instanceof String)
			return convertFromString((String) raw, itemType, collection);
		else if (raw instanceof Collection<?>)
			return convertFromCollection((Collection<?>) raw, itemType, collection);
		else
			throw new TypeConverterException(
					String.format("Expected string or collection for List<T>, got %s", raw.getClass().getName()));
	}

	private Object convertFromString(String rawString, TypeDescriptor itemType, Collection<Object> collection) {
		String[] parts = rawString.split(",");
		for (String part : parts)
			collection.add(typeConverter.convert(part.trim(), itemType));
		return collection;
	}

	private Object convertFromCollection(Collection<?> rawCollection, TypeDescriptor itemType, Collection<Object> collection) {
		for (Object element : rawCollection)
			collection.add(typeConverter.convert(element, itemType));
		return collection;
	}

}
