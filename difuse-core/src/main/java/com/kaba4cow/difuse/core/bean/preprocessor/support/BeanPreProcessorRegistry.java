package com.kaba4cow.difuse.core.bean.preprocessor.support;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.annotation.system.SystemDependency;
import com.kaba4cow.difuse.core.bean.preprocessor.BeanPreProcessor;

@SystemBean
public class BeanPreProcessorRegistry {

	private static final Logger log = LoggerFactory.getLogger("BeanPreProcessorRegistry");

	@SystemDependency
	private BeanPreProcessorFactory beanPreProcessorFactory;

	private final Map<Class<? extends BeanPreProcessor>, BeanPreProcessor> registry = new ConcurrentHashMap<>();

	public void registerBeanPreProcessor(Class<? extends BeanPreProcessor> type) {
		registry.computeIfAbsent(type, beanPreProcessorFactory::createBeanPreProcessor);
		log.debug("Registered BeanPreProcessor {}", type);
	}

	public Collection<BeanPreProcessor> getBeanPreProcessors() {
		return Collections.unmodifiableCollection(registry.values());
	}

}
