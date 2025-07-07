package com.kaba4cow.difuse.core.bean.processor.pre.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import com.kaba4cow.difuse.core.annotation.access.AccessProvider;
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
	public boolean process(BeanSource<?> beanSource) {
		BeanProtector protector = beanSource.getBeanProtector();
		Set<Annotation> annotations = findAccessProviderAnnotations(beanSource.getSourceElement());
		for (Annotation annotation : annotations) {
			BeanAccessProvider<?> accessProvider = registry.getProvider(annotation.annotationType());
			protector.addAccessProvider(annotation, accessProvider);
		}
		return true;
	}

	private Set<Annotation> findAccessProviderAnnotations(AnnotatedElement element) {
		return Arrays.stream(element.getAnnotations())//
				.filter(annotation -> annotation.annotationType().isAnnotationPresent(AccessProvider.class))//
				.collect(Collectors.toSet());
	}

}
