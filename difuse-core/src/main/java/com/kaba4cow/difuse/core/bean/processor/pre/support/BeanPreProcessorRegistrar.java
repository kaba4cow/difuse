package com.kaba4cow.difuse.core.bean.processor.pre.support;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.bean.processor.pre.BeanPreProcessor;
import com.kaba4cow.difuse.core.context.ContextScanner;
import com.kaba4cow.difuse.core.util.LoggingTimer;
import com.kaba4cow.difuse.core.util.reflections.PackageScanner;

@SystemBean
public class BeanPreProcessorRegistrar {

	private static final Logger log = LoggerFactory.getLogger("BeanPreProcessorRegistrar");

	@Provided
	private ContextScanner contextScanner;

	@Provided
	private BeanPreProcessorRegistry beanPreProcessorRegistry;

	public void registerBeanPreProcessors(Class<?> sourceClass) {
		try (LoggingTimer timer = new LoggingTimer(log, "Registering BeanPreProcessors for {}...", sourceClass)) {
			PackageScanner packageScanner = contextScanner.getScanner(sourceClass);
			Set<Class<? extends BeanPreProcessor>> beanProcessorClasses = packageScanner
					.searchSubTypesOf(BeanPreProcessor.class);
			for (Class<? extends BeanPreProcessor> beanProcessorClass : beanProcessorClasses)
				beanPreProcessorRegistry.registerBeanPreProcessor(beanProcessorClass);
		}
	}

}
