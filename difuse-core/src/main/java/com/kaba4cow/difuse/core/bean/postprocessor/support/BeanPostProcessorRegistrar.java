package com.kaba4cow.difuse.core.bean.postprocessor.support;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.bean.postprocessor.BeanPostProcessor;
import com.kaba4cow.difuse.core.context.ContextScanner;
import com.kaba4cow.difuse.core.util.LoggingTimer;
import com.kaba4cow.difuse.core.util.reflections.PackageScanner;

@SystemBean
public class BeanPostProcessorRegistrar {

	private static final Logger log = LoggerFactory.getLogger("BeanPostProcessorRegistrar");

	@Provided
	private ContextScanner contextScanner;

	@Provided
	private BeanPostProcessorRegistry beanPostProcessorRegistry;

	public void registerBeanPostProcessors(Class<?> sourceClass) {
		try (LoggingTimer timer = new LoggingTimer(log, "Registering BeanPostProcessors for {}...", sourceClass)) {
			PackageScanner packageScanner = contextScanner.getScanner(sourceClass);
			Set<Class<? extends BeanPostProcessor>> beanProcessorClasses = packageScanner
					.searchClassesOf(BeanPostProcessor.class);
			for (Class<? extends BeanPostProcessor> beanProcessorClass : beanProcessorClasses)
				beanPostProcessorRegistry.registerBeanPostProcessor(beanProcessorClass);
		}
	}

}
