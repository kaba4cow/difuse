package com.kaba4cow.difuse.core.type.converter.support;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.type.TypeDescriptor;
import com.kaba4cow.difuse.core.type.converter.TypeConverter;
import com.kaba4cow.difuse.core.type.converter.TypeConverterException;
import com.kaba4cow.difuse.core.type.converter.support.converter.ArrayConverter;
import com.kaba4cow.difuse.core.type.converter.support.converter.CollectionConverter;
import com.kaba4cow.difuse.core.type.converter.support.converter.EnumConverter;
import com.kaba4cow.difuse.core.type.converter.support.converter.MapConverter;
import com.kaba4cow.difuse.core.type.converter.support.converter.OptionalConverter;

@SystemBean
public class GlobalTypeConverter {

	@Provided
	private TypeConverterRegistry registry;

	private final ArrayConverter arrayConverter = new ArrayConverter(this);

	private final CollectionConverter collectionConverter = new CollectionConverter(this);

	private final MapConverter mapConverter = new MapConverter(this);

	private final OptionalConverter optionalConverter = new OptionalConverter(this);

	private final EnumConverter enumConverter = new EnumConverter();

	public Object convert(Object raw, TypeDescriptor type) {
		try {
			if (type.isClass())
				return convertToClass(raw, type);
			else if (type.isParameterized())
				return convertToParameterizedType(raw, type);
			else
				throw new TypeConverterException(String.format("Unsupported type: %s", type));
		} catch (Exception exception) {
			throw new TypeConverterException(String.format("Could not convert '%s' to %s", raw, type), exception);
		}
	}

	private Object convertToParameterizedType(Object raw, TypeDescriptor type) {
		TypeDescriptor rawType = type.getRawType();
		TypeDescriptor[] typeArgs = type.getGenericArguments();
		if (typeArgs.length == 1) {
			TypeDescriptor itemType = typeArgs[0];
			if (rawType.isType(List.class))
				return collectionConverter.convert(raw, itemType, new ArrayList<>());
			else if (rawType.isType(Set.class))
				return collectionConverter.convert(raw, itemType, new HashSet<>());
			else if (rawType.isType(Optional.class))
				return optionalConverter.convert(raw, itemType);
		} else if (typeArgs.length == 2) {
			TypeDescriptor keyType = typeArgs[0];
			TypeDescriptor valueType = typeArgs[1];
			if (rawType.isType(Map.class) && keyType.isType(String.class))
				return mapConverter.convert(raw, valueType);
		}
		throw new TypeConverterException(String.format("Unsupported parameterized type %s", type));
	}

	private Object convertToClass(Object raw, TypeDescriptor type) {
		if (Objects.isNull(raw))
			return null;
		else if (type.isInstance(raw))
			return raw;
		else if (type.isArray())
			return arrayConverter.convert(raw, type.getComponentType());
		else if (raw instanceof String)
			return type.isEnum()//
					? enumConverter.convert((String) raw, type)//
					: convertFromString((String) raw, type);
		else
			throw new TypeConverterException(String.format("Could not convert '%s' to %s", raw, type));
	}

	private Object convertFromString(String raw, TypeDescriptor type) {
		TypeConverter<?> converter = registry.getConverter(type.getClassType());
		if (Objects.isNull(converter))
			throw new TypeConverterException(String.format("Found no converter for %s", type));
		return converter.convert(raw);
	}

}
