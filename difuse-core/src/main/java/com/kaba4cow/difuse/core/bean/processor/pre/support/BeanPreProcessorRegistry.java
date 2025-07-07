package com.kaba4cow.difuse.core.bean.processor.pre.support;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.bean.processor.BeanProcessor;
import com.kaba4cow.difuse.core.bean.processor.pre.BeanPreProcessor;

@SystemBean
public class BeanPreProcessorRegistry {

	private static final Logger log = LoggerFactory.getLogger("BeanPreProcessorRegistry");

	@Provided
	private BeanPreProcessorFactory beanPreProcessorFactory;

	private final Map<Class<? extends BeanPreProcessor>, BeanPreProcessor> registry = new ConcurrentHashMap<>();

	public void registerBeanPreProcessor(Class<? extends BeanPreProcessor> type) {
		registry.computeIfAbsent(type, beanPreProcessorFactory::createBeanPreProcessor);
		log.debug("Registered BeanPreProcessor {}", type.getName());
	}

	public Collection<BeanPreProcessor> getBeanPreProcessors() {
		return Collections.unmodifiableCollection(registry.values());
	}

	public Set<BeanPreProcessor> getActiveBeanPreProcessors() {
		return registry.values().stream()//
				.filter(BeanProcessor::isActive)//
				.collect(Collectors.toSet());
	}

}
