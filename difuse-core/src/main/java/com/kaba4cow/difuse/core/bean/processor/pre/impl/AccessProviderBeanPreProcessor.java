package com.kaba4cow.difuse.core.bean.processor.pre.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import com.kaba4cow.difuse.core.annotation.access.provider.AccessProvider;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.bean.access.provider.BeanAccessProvider;
import com.kaba4cow.difuse.core.bean.access.provider.support.BeanAccessProviderRegistry;
import com.kaba4cow.difuse.core.bean.processor.pre.BeanPreProcessor;
import com.kaba4cow.difuse.core.bean.protector.BeanProtector;
import com.kaba4cow.difuse.core.bean.source.BeanSource;

public class AccessProviderBeanPreProcessor implements BeanPreProcessor {

	@Provided
	private BeanAccessProviderRegistry registry;

	@Override
	public <T extends BeanSource<?>> T preProcess(T beanSource) {
		BeanProtector protector = beanSource.getBeanProtector();
		Set<Annotation> annotations = findAccessProviderAnnotations(beanSource.getSourceElement());
		for (Annotation annotation : annotations)
			addAccessProvider(annotation, protector);
		return beanSource;
	}

	@SuppressWarnings("unchecked")
	private <T extends Annotation> void addAccessProvider(T annotation, BeanProtector protector) {
		Class<T> annotationType = (Class<T>) annotation.annotationType();
		BeanAccessProvider<T> accessProvider = (BeanAccessProvider<T>) registry.getProvider(annotationType);
		protector.addAccessProvider(annotation, accessProvider);
	}

	private Set<Annotation> findAccessProviderAnnotations(AnnotatedElement element) {
		return Arrays.stream(element.getAnnotations())//
				.filter(annotation -> annotation.annotationType().isAnnotationPresent(AccessProvider.class))//
				.collect(Collectors.toSet());
	}

}
