package com.kaba4cow.difuse.core.bean.provider.support;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.bean.provider.BeanProvider;
import com.kaba4cow.difuse.core.bean.source.BeanSource;

@SystemBean
public class BeanProviderRegistry {

	private static final Logger log = LoggerFactory.getLogger("BeanProviderRegistry");

	private final Map<String, Set<BeanProvider<?>>> providersByName = new ConcurrentHashMap<>();

	private final Map<BeanSource<?>, BeanProvider<?>> providersBySource = new ConcurrentHashMap<>();

	public void register(BeanProvider<?> beanProvider) {
		BeanSource<?> beanSource = beanProvider.getBeanSource();
		String name = beanSource.getInfo().getName();
		if (providersByName.computeIfAbsent(name, key -> ConcurrentHashMap.newKeySet()).add(beanProvider)) {
			providersBySource.put(beanSource, beanProvider);
			log.debug("Registered {}", beanProvider);
		}
	}

	public List<BeanProvider<?>> findByClass(Class<?> beanClass) {
		return providersByName.values().stream()//
				.flatMap(Set::stream)//
				.filter(beanProvider -> beanClass.isAssignableFrom(beanProvider.getBeanSource().getBeanClass()))//
				.collect(Collectors.toList());
	}

	public List<BeanProvider<?>> findByNameAndClass(String name, Class<?> beanClass) {
		if (!providersByName.containsKey(name))
			return Collections.emptyList();
		return providersByName.get(name).stream()//
				.filter(beanProvider -> beanClass.isAssignableFrom(beanProvider.getBeanSource().getBeanClass()))//
				.collect(Collectors.toList());
	}

	public List<BeanProvider<?>> findEagerBeanProviders(Class<? extends BeanProvider<?>> beanProviderClass) {
		return providersByName.values().stream()//
				.flatMap(Set::stream)//
				.filter(beanProvider -> beanProvider.getBeanSource().getInfo().isEager())//
				.filter(beanProviderClass::isInstance)//
				.collect(Collectors.toList());
	}

	public Optional<BeanProvider<?>> findBySource(BeanSource<?> beanSource) {
		return Optional.ofNullable(providersBySource.get(beanSource));
	}

}
