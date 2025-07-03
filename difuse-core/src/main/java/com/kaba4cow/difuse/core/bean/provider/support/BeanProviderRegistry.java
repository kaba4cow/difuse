package com.kaba4cow.difuse.core.bean.provider.support;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.application.CoreComponent;
import com.kaba4cow.difuse.core.bean.provider.BeanProvider;

@CoreComponent
public class BeanProviderRegistry {

	private static final Logger log = LoggerFactory.getLogger("BeanProviderRegistry");

	private final Map<String, Set<BeanProvider<?>>> registry = new ConcurrentHashMap<>();

	public void register(BeanProvider<?> beanProvider) {
		String name = beanProvider.getBeanSource().getName();
		if (!registry.containsKey(name))
			registry.put(name, ConcurrentHashMap.newKeySet());
		registry.get(name).add(beanProvider);
		log.debug("Registered {}", beanProvider);
	}

	public List<BeanProvider<?>> findByClass(Class<?> beanClass) {
		return registry.values().stream()//
				.flatMap(Set::stream)//
				.filter(beanProvider -> beanClass.isAssignableFrom(beanProvider.getBeanSource().getBeanClass()))//
				.collect(Collectors.toList());
	}

	public List<BeanProvider<?>> findByNameAndClass(String name, Class<?> beanClass) {
		if (!registry.containsKey(name))
			return Collections.emptyList();
		return registry.get(name).stream()//
				.filter(beanProvider -> beanClass.isAssignableFrom(beanProvider.getBeanSource().getBeanClass()))//
				.collect(Collectors.toList());
	}

	public List<BeanProvider<?>> findEagerBeanProviders(Class<? extends BeanProvider<?>> beanProviderClass) {
		return registry.values().stream()//
				.flatMap(Set::stream)//
				.filter(beanProvider -> !beanProvider.getBeanSource().isLazy())//
				.filter(beanProviderClass::isInstance)//
				.collect(Collectors.toList());
	}

}
