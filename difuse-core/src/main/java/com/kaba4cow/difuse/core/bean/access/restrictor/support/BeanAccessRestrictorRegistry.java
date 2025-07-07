package com.kaba4cow.difuse.core.bean.access.restrictor.support;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.bean.access.restrictor.BeanAccessRestrictor;

@SystemBean
public class BeanAccessRestrictorRegistry {

	private static final Logger log = LoggerFactory.getLogger("BeanAccessRestrictorRegistry");

	private final Map<Class<? extends Annotation>, BeanAccessRestrictor<?>> registry = new ConcurrentHashMap<>();

	@Provided
	private BeanAccessRestrictorFactory restrictorFactory;

	public BeanAccessRestrictor<?> getProvider(Class<? extends Annotation> annotation) {
		return registry.computeIfAbsent(annotation, this::registerRestrictor);
	}

	private BeanAccessRestrictor<?> registerRestrictor(Class<? extends Annotation> annotation) {
		BeanAccessRestrictor<?> provider = restrictorFactory.createRestrictor(annotation);
		log.debug("Registered BeanAccessRestrictor {}", provider.getClass().getName());
		return provider;
	}

}
