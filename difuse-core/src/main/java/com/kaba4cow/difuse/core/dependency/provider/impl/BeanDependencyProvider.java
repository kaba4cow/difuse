package com.kaba4cow.difuse.core.dependency.provider.impl;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Array;
import java.util.HashMap;
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
import com.kaba4cow.difuse.core.type.TypeDescriptor;

@SystemBean
public class BeanDependencyProvider implements DependencyProvider {

	@Provided
	private AccessibleSystemBeanRegistry systemBeanRegistry;

	@Provided
	private BeanProviderRegistry beanProviderRegistry;

	@Override
	public Object provideDependency(AnnotatedElement element, TypeDescriptor type, DependencyConsumer dependencyConsumer) {
		if (type.isArray())
			return provideArrayDependency(type, dependencyConsumer);
		if (type.isParameterized())
			return provideParameterizedTypeDependency(type, dependencyConsumer);
		if (type.isClass())
			return provideSingleDependency(element, type, dependencyConsumer);
		throw new DifuseException(String.format("Unsupported dependency type: %s", type));
	}

	private Object provideSingleDependency(AnnotatedElement element, TypeDescriptor descriptor,
			DependencyConsumer dependencyConsumer) {
		Class<?> type = descriptor.getClassType();
		if (systemBeanRegistry.containsBean(type))
			return systemBeanRegistry.getBean(type);
		List<BeanProvider<?>> providers;
		if (element.isAnnotationPresent(Named.class)) {
			String name = element.getAnnotation(Named.class).value();
			providers = beanProviderRegistry.findByNameAndClass(name, type);
			if (providers.isEmpty())
				throw new DifuseException(String.format("Found no beans of type %s with name '%s'", descriptor, name));
			if (providers.size() > 1)
				throw new DifuseException(String.format("Found multiple beans of type %s with name '%s'", descriptor, name));
		} else {
			providers = beanProviderRegistry.findByClass(type);
			if (providers.isEmpty())
				throw new DifuseException(String.format("Found no beans of type %s", descriptor));
			if (providers.size() > 1)
				throw new DifuseException(String.format("Found multiple beans of type %s", descriptor));
		}
		return providers.get(0).provideProtectedBean(dependencyConsumer);
	}

	private Object provideParameterizedTypeDependency(TypeDescriptor descriptor, DependencyConsumer dependencyConsumer) {
		TypeDescriptor raw = descriptor.getRawType();
		if (raw.isType(List.class))
			return provideListDependency(descriptor, dependencyConsumer);
		if (raw.isType(Map.class))
			return provideMapDependency(descriptor, dependencyConsumer);
		throw new DifuseException(String.format("Unsupported parameterized type: %s", descriptor));
	}

	private Object provideListDependency(TypeDescriptor descriptor, DependencyConsumer dependencyConsumer) {
		TypeDescriptor item = descriptor.getGenericArguments()[0];
		if (!item.isClass())
			throw new DifuseException(String.format("Unsupported list type argument: %s", item));
		return beanProviderRegistry.findByClass(item.getClassType()).stream()//
				.map(provider -> provider.provideProtectedBean(dependencyConsumer))//
				.collect(Collectors.toList());
	}

	private Object provideMapDependency(TypeDescriptor descriptor, DependencyConsumer dependencyConsumer) {
		TypeDescriptor[] args = descriptor.getGenericArguments();
		TypeDescriptor keyType = args[0];
		TypeDescriptor valueType = args[1];
		if (!keyType.isType(String.class))
			throw new DifuseException(String.format("Unsupported map key type: %s", keyType));
		if (!valueType.isClass())
			throw new DifuseException(String.format("Unsupported map value type: %s", valueType));
		Class<?> valueClass = valueType.getClassType();
		Map<String, Object> map = new HashMap<>();
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

	private Object provideArrayDependency(TypeDescriptor descriptor, DependencyConsumer dependencyConsumer) {
		Class<?> componentType = descriptor.getComponentType().getClassType();
		List<BeanProvider<?>> providers = beanProviderRegistry.findByClass(componentType);
		Object array = Array.newInstance(componentType, providers.size());
		for (int i = 0; i < providers.size(); i++)
			Array.set(array, i, providers.get(i).provideProtectedBean(dependencyConsumer));
		return array;
	}

}
