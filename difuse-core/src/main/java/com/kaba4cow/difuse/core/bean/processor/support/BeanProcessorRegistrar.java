package com.kaba4cow.difuse.core.bean.processor.support;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.application.CoreComponent;
import com.kaba4cow.difuse.core.annotation.application.CoreDependency;
import com.kaba4cow.difuse.core.application.PackageScannerPool;
import com.kaba4cow.difuse.core.bean.processor.BeanProcessor;
import com.kaba4cow.difuse.core.util.ExecutionTimer;
import com.kaba4cow.difuse.core.util.reflections.PackageScanner;

@CoreComponent
public class BeanProcessorRegistrar {

	private static final Logger log = LoggerFactory.getLogger("BeanProcessorRegistrar");

	@CoreDependency
	private PackageScannerPool packageScannerPool;

	@CoreDependency
	private BeanProcessorRegistry beanProcessorRegistry;

	public void registerBeanProcessors(Class<?> sourceClass) {
		log.info("Registering BeanProcessors for {}...", sourceClass);
		ExecutionTimer timer = new ExecutionTimer().start();

		PackageScanner packageScanner = packageScannerPool.getPackageScanner(sourceClass);
		Set<Class<? extends BeanProcessor>> beanProcessorClasses = packageScanner.searchClassesOf(BeanProcessor.class);
		for (Class<? extends BeanProcessor> beanProcessorClass : beanProcessorClasses)
			beanProcessorRegistry.registerBeanProcessor(beanProcessorClass);

		log.info("BeanProcessor registration took {} ms", timer.finish().getExecutionMillis());
	}

}
