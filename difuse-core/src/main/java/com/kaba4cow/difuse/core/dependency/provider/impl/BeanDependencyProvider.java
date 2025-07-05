package com.kaba4cow.difuse.core.dependency.provider.impl;

import java.lang.reflect.AnnotatedElement;
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
import com.kaba4cow.difuse.core.system.bean.SystemBeanRegistry;

@SystemBean
public class BeanDependencyProvider implements DependencyProvider {

	@Provided
	private SystemBeanRegistry systemBeanRegistry;

	@Provided
	private BeanProviderRegistry beanProviderRegistry;

	@Override
	public Object provideDependency(AnnotatedElement element, Type type, DependencyConsumer dependencyConsumer) {
		if (type instanceof Class<?>) {
			Class<?> dependencyClass = (Class<?>) type;
			if (systemBeanRegistry.containsAccessibleBean(dependencyClass))
				return systemBeanRegistry.getAccessibleBean((Class<?>) type);
			return provideSingleDependency(element, dependencyClass, dependencyConsumer);
		} else if (type instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) type;
			Type rawType = parameterizedType.getRawType();
			if (rawType == List.class)
				return provideListDependency(parameterizedType, dependencyConsumer);
			if (rawType == Map.class)
				return provideMapDependency(parameterizedType, dependencyConsumer);
		}
		throw new DifuseException(String.format("Unsupported dependency type: %s", type));
	}

	private Object provideSingleDependency(AnnotatedElement element, Class<?> type, DependencyConsumer dependencyConsumer) {
		if (element.isAnnotationPresent(Named.class)) {
			String targetName = element.getAnnotation(Named.class).value();
			List<BeanProvider<?>> namedBeanProviders = beanProviderRegistry.findByNameAndClass(targetName, type);
			if (namedBeanProviders.isEmpty())
				throw new DifuseException(String.format("Found no beans of type %s with name '%s'", type, targetName));
			if (namedBeanProviders.size() > 1)
				throw new DifuseException(String.format("Found multiple beans of type %s with name '%s'", type, targetName));
			return namedBeanProviders.get(0).provideProtectedBean(dependencyConsumer);
		}
		List<BeanProvider<?>> beanProviders = beanProviderRegistry.findByClass(type);
		if (beanProviders.isEmpty())
			throw new DifuseException(String.format("Found no beans of type %s", type));
		if (beanProviders.size() > 1)
			throw new DifuseException(String.format("Found multiple beans of type %s", type));
		return beanProviders.get(0).provideProtectedBean(dependencyConsumer);
	}

	private Object provideListDependency(ParameterizedType type, DependencyConsumer dependencyConsumer) {
		Type itemType = type.getActualTypeArguments()[0];
		if (itemType instanceof Class<?>) {
			Class<?> itemClass = (Class<?>) itemType;
			return beanProviderRegistry.findByClass(itemClass).stream()//
					.map(provider -> provider.provideProtectedBean(dependencyConsumer))//
					.collect(Collectors.toList());
		} else
			throw new DifuseException(String.format("Unsupported list type argument: %s", itemType));
	}

	private Object provideMapDependency(ParameterizedType type, DependencyConsumer dependencyConsumer) {
		Type keyType = type.getActualTypeArguments()[0];
		Type valueType = type.getActualTypeArguments()[1];
		if (keyType != String.class)
			throw new DifuseException(String.format("Unsupported map key type argument: %s", keyType));
		if (!(valueType instanceof Class<?>))
			throw new DifuseException(String.format("Unsupported map value type argument: %s", valueType));
		Class<?> valueClass = (Class<?>) valueType;
		Map<String, Object> map = new LinkedHashMap<>();
		for (BeanProvider<?> provider : beanProviderRegistry.findByClass(valueClass)) {
			String name = provider.getBeanSource().getInfo().getName();
			Object previous = map.putIfAbsent(name, provider.provideProtectedBean(dependencyConsumer));
			if (Objects.nonNull(previous))
				throw new DifuseException(
						String.format("Duplicate bean name '%s' for type Map<String, %s>. Conflicting classes: %s and %s", name,
								valueClass.getSimpleName(), previous.getClass().getName(),
								provider.getBeanSource().getBeanClass().getName()));
		}
		return map;
	}

}
