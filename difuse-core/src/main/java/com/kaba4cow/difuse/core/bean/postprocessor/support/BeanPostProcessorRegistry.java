package com.kaba4cow.difuse.core.bean.postprocessor.support;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.system.SystemComponent;
import com.kaba4cow.difuse.core.annotation.system.SystemDependency;
import com.kaba4cow.difuse.core.bean.postprocessor.BeanPostProcessor;

@SystemComponent
public class BeanPostProcessorRegistry {

	private static final Logger log = LoggerFactory.getLogger("BeanPostProcessorRegistry");

	@SystemDependency
	private BeanPostProcessorFactory beanPostProcessorFactory;

	private final Map<Class<? extends BeanPostProcessor>, BeanPostProcessor> registry = new ConcurrentHashMap<>();

	public void registerBeanPostProcessor(Class<? extends BeanPostProcessor> type) {
		registry.computeIfAbsent(type, beanPostProcessorFactory::createBeanPostProcessor);
		log.debug("Registered BeanPostProcessor {}", type);
	}

	public List<BeanPostProcessor> getBeanPostProcessors() {
		return registry.values().stream()//
				.sorted(Comparator.comparing(BeanPostProcessor::getLifecyclePhase))//
				.collect(Collectors.toList());
	}

}
