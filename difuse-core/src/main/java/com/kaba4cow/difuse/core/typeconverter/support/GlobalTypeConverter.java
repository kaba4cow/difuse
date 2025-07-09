package com.kaba4cow.difuse.core.typeconverter.support;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.typeconverter.TypeConverter;
import com.kaba4cow.difuse.core.typeconverter.TypeConverterException;
import com.kaba4cow.difuse.core.typeconverter.support.converter.ArrayConverter;
import com.kaba4cow.difuse.core.typeconverter.support.converter.CollectionConverter;
import com.kaba4cow.difuse.core.typeconverter.support.converter.EnumConverter;
import com.kaba4cow.difuse.core.typeconverter.support.converter.MapConverter;
import com.kaba4cow.difuse.core.typeconverter.support.converter.OptionalConverter;

@SystemBean
public class GlobalTypeConverter {

	@Provided
	private TypeConverterRegistry registry;

	private final ArrayConverter arrayConverter = new ArrayConverter(this);

	private final CollectionConverter collectionConverter = new CollectionConverter(this);

	private final MapConverter mapConverter = new MapConverter(this);

	private final OptionalConverter optionalConverter = new OptionalConverter(this);

	private final EnumConverter enumConverter = new EnumConverter();

	public Object convert(Object raw, Type type) {
		try {
			if (type instanceof Class<?>)
				return convertToClass(raw, (Class<?>) type);
			else if (type instanceof ParameterizedType)
				return convertToParameterizedType(raw, (ParameterizedType) type);
			else
				throw new TypeConverterException(String.format("Unsupported type: %s", type));
		} catch (Exception exception) {
			throw new TypeConverterException(String.format("Could not convert '%s' to %s", raw, type), exception);
		}
	}

	private Object convertToParameterizedType(Object raw, ParameterizedType type) {
		Type rawType = type.getRawType();
		Type[] typeArgs = type.getActualTypeArguments();
		if (typeArgs.length == 1) {
			Type itemType = typeArgs[0];
			if (rawType == List.class)
				return collectionConverter.convert(raw, itemType, new ArrayList<>());
			else if (rawType == Set.class)
				return collectionConverter.convert(raw, itemType, new HashSet<>());
			else if (rawType == Optional.class)
				return optionalConverter.convert(raw, itemType);
		} else if (typeArgs.length == 2) {
			Type keyType = typeArgs[0];
			Type valueType = typeArgs[1];
			if (rawType == Map.class && keyType == String.class)
				return mapConverter.convert(raw, valueType);
		}
		throw new TypeConverterException(String.format("Unsupported parameterized type %s", type));
	}

	private Object convertToClass(Object raw, Class<?> type) {
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
			throw new TypeConverterException(String.format("Could not convert '%s' to %s", raw, type.getName()));
	}

	private Object convertFromString(String raw, Class<?> type) {
		TypeConverter<?> converter = registry.getConverter(type);
		if (Objects.isNull(converter))
			throw new TypeConverterException(String.format("Found no converter for %s", type.getName()));
		return converter.convert(raw);
	}

}
