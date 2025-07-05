package com.kaba4cow.difuse.core.bean.processor.support;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.system.SystemComponent;
import com.kaba4cow.difuse.core.annotation.system.SystemDependency;
import com.kaba4cow.difuse.core.bean.processor.BeanProcessor;

@SystemComponent
public class BeanProcessorRegistry {

	private static final Logger log = LoggerFactory.getLogger("BeanProcessorRegistry");

	@SystemDependency
	private BeanProcessorFactory beanProcessorFactory;

	private final Map<Class<? extends BeanProcessor>, BeanProcessor> registry = new ConcurrentHashMap<>();

	public void registerBeanProcessor(Class<? extends BeanProcessor> type) {
		registry.computeIfAbsent(type, beanProcessorFactory::createBeanProcessor);
		log.debug("Registered BeanProcessor {}", type);
	}

	public List<BeanProcessor> getBeanProcessors() {
		return registry.values().stream()//
				.sorted(Comparator.comparing(BeanProcessor::getLifecyclePhase))//
				.collect(Collectors.toList());
	}

}
