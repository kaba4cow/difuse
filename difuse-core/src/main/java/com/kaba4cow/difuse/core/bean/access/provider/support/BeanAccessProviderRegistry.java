package com.kaba4cow.difuse.core.bean.access.provider.support;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.bean.access.provider.BeanAccessProvider;

@SystemBean
public class BeanAccessProviderRegistry {

	private static final Logger log = LoggerFactory.getLogger("BeanAccessProviderRegistry");

	private final Map<Class<? extends Annotation>, BeanAccessProvider<?>> registry = new ConcurrentHashMap<>();

	@Provided
	private BeanAccessProviderFactory providerFactory;

	public BeanAccessProvider<?> getProvider(Class<? extends Annotation> annotation) {
		return registry.computeIfAbsent(annotation, this::registerProvider);
	}

	private BeanAccessProvider<?> registerProvider(Class<? extends Annotation> annotation) {
		BeanAccessProvider<?> provider = providerFactory.createProvider(annotation);
		log.debug("Registered BeanAccessProvider {}", provider.getClass().getName());
		return provider;
	}

}
