package com.kaba4cow.difuse.core.dependency.provider.impl;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.kaba4cow.difuse.core.DifuseException;
import com.kaba4cow.difuse.core.annotation.bean.Named;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.bean.provider.BeanProvider;
import com.kaba4cow.difuse.core.bean.provider.support.BeanProviderRegistry;
import com.kaba4cow.difuse.core.dependency.DependencyConsumer;
import com.kaba4cow.difuse.core.dependency.provider.DependencyProvider;
import com.kaba4cow.difuse.core.system.bean.registry.impl.AccessibleSystemBeanRegistry;

@SystemBean
public class BeanDependencyProvider implements DependencyProvider {

	@Provided
	private AccessibleSystemBeanRegistry systemBeanRegistry;

	@Provided
	private BeanProviderRegistry beanProviderRegistry;

	@Override
	public Object provideDependency(AnnotatedElement element, Type type, DependencyConsumer dependencyConsumer) {
		if (type instanceof GenericArrayType || (type instanceof Class<?> && ((Class<?>) type).isArray()))
			return provideArrayDependency(type, dependencyConsumer);
		if (type instanceof ParameterizedType)
			return provideParameterizedTypeDependency((ParameterizedType) type, dependencyConsumer);
		if (type instanceof Class<?>)
			return provideSingleDependency(element, (Class<?>) type, dependencyConsumer);
		throw new DifuseException(String.format("Unsupported dependency type %s", type));
	}

	private Object provideSingleDependency(AnnotatedElement element, Class<?> type, DependencyConsumer dependencyConsumer) {
		if (systemBeanRegistry.containsBean(type))
			return systemBeanRegistry.getBean(type);
		List<BeanProvider<?>> providers;
		if (element.isAnnotationPresent(Named.class)) {
			String name = element.getAnnotation(Named.class).value();
			providers = beanProviderRegistry.findByNameAndClass(name, type);
			if (providers.isEmpty())
				throw new DifuseException(String.format("Found no beans of type %s with name '%s'", type, name));
			if (providers.size() > 1)
				throw new DifuseException(String.format("Found multiple beans of type %s with name '%s'", type, name));
		} else {
			providers = beanProviderRegistry.findByClass(type);
			if (providers.isEmpty())
				throw new DifuseException(String.format("Found no beans of type %s", type));
			if (providers.size() > 1)
				throw new DifuseException(String.format("Found multiple beans of type %s", type));
		}
		return providers.get(0).provideProtectedBean(dependencyConsumer);
	}

	private Object provideArrayDependency(Type type, DependencyConsumer dependencyConsumer) {
		Class<?> componentType = extractComponentType(type);
		List<BeanProvider<?>> providers = beanProviderRegistry.findByClass(componentType);
		Object array = Array.newInstance(componentType, providers.size());
		for (int i = 0; i < providers.size(); i++)
			Array.set(array, i, providers.get(i).provideProtectedBean(dependencyConsumer));
		return array;
	}

	private Class<?> extractComponentType(Type type) {
		if (type instanceof Class<?> && ((Class<?>) type).isArray())
			return ((Class<?>) type).getComponentType();
		if (type instanceof GenericArrayType) {
			Type component = ((GenericArrayType) type).getGenericComponentType();
			if (component instanceof Class<?>)
				return (Class<?>) component;
		}
		throw new DifuseException(String.format("Unsupported array type %s", type));
	}

	private Object provideParameterizedTypeDependency(ParameterizedType type, DependencyConsumer dependencyConsumer) {
		Type raw = type.getRawType();
		if (raw == List.class)
			return provideListDependency(type, dependencyConsumer);
		if (raw == Map.class)
			return provideMapDependency(type, dependencyConsumer);
		throw new DifuseException(String.format("Unsupported parameterized type %s", type));
	}

	private Object provideListDependency(ParameterizedType type, DependencyConsumer dependencyConsumer) {
		Type itemType = type.getActualTypeArguments()[0];
		if (!(itemType instanceof Class<?>))
			throw new DifuseException(String.format("Unsupported list type argument %s", itemType));
		Class<?> itemClass = (Class<?>) itemType;
		return beanProviderRegistry.findByClass(itemClass).stream()//
				.map(p -> p.provideProtectedBean(dependencyConsumer))//
				.collect(Collectors.toList());
	}

	private Object provideMapDependency(ParameterizedType type, DependencyConsumer dependencyConsumer) {
		Type keyType = type.getActualTypeArguments()[0];
		Type valueType = type.getActualTypeArguments()[1];
		if (keyType != String.class)
			throw new DifuseException(String.format("Unsupported map key type %s", keyType));
		if (!(valueType instanceof Class<?>))
			throw new DifuseException(String.format("Unsupported map value type %s", valueType));
		Class<?> valueClass = (Class<?>) valueType;
		Map<String, Object> map = new LinkedHashMap<>();
		for (BeanProvider<?> provider : beanProviderRegistry.findByClass(valueClass)) {
			String name = provider.getBeanSource().getInfo().getName();
			Object bean = provider.provideProtectedBean(dependencyConsumer);
			Object previous = map.putIfAbsent(name, bean);
			if (Objects.nonNull(previous))
				throw new DifuseException(String.format(
						"Duplicate bean name '%s' for type Map<String, %s>. Conflicting: %s and %s", name, valueClass.getName(),
						previous.getClass().getName(), provider.getBeanSource().getBeanClass().getName()));
		}
		return map;
	}

}
