package com.kaba4cow.difuse.core.bean.processor.post.support;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.bean.processor.BeanProcessor;
import com.kaba4cow.difuse.core.bean.processor.post.BeanPostProcessor;

@SystemBean
public class BeanPostProcessorRegistry {

	private static final Logger log = LoggerFactory.getLogger("BeanPostProcessorRegistry");

	@Provided
	private BeanPostProcessorFactory beanPostProcessorFactory;

	private final Map<Class<? extends BeanPostProcessor>, BeanPostProcessor> registry = new ConcurrentHashMap<>();

	public void registerBeanPostProcessor(Class<? extends BeanPostProcessor> type) {
		registry.computeIfAbsent(type, beanPostProcessorFactory::createBeanPostProcessor);
		log.debug("Registered BeanPostProcessor {} [{}]", type.getName(), registry.get(type).getLifecyclePhase());
	}

	public List<BeanPostProcessor> getBeanPostProcessors() {
		return registry.values().stream()//
				.collect(Collectors.toList());
	}

	public List<BeanPostProcessor> getActiveBeanPostProcessors() {
		return registry.values().stream()//
				.filter(BeanProcessor::isActive)//
				.collect(Collectors.toList());
	}

}
