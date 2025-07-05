package com.kaba4cow.difuse.core.bean.preprocessor.support;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.system.SystemComponent;
import com.kaba4cow.difuse.core.annotation.system.SystemDependency;
import com.kaba4cow.difuse.core.bean.preprocessor.BeanPreProcessor;
import com.kaba4cow.difuse.core.system.PackageScannerPool;
import com.kaba4cow.difuse.core.util.ExecutionTimer;
import com.kaba4cow.difuse.core.util.reflections.PackageScanner;

@SystemComponent
public class BeanPreProcessorRegistrar {

	private static final Logger log = LoggerFactory.getLogger("BeanPreProcessorRegistrar");

	@SystemDependency
	private PackageScannerPool packageScannerPool;

	@SystemDependency
	private BeanPreProcessorRegistry beanPreProcessorRegistry;

	public void registerBeanPreProcessors(Class<?> sourceClass) {
		log.info("Registering BeanPreProcessors for {}...", sourceClass);
		ExecutionTimer timer = new ExecutionTimer().start();

		PackageScanner packageScanner = packageScannerPool.getPackageScanner(sourceClass);
		Set<Class<? extends BeanPreProcessor>> beanProcessorClasses = packageScanner.searchClassesOf(BeanPreProcessor.class);
		for (Class<? extends BeanPreProcessor> beanProcessorClass : beanProcessorClasses)
			beanPreProcessorRegistry.registerBeanPreProcessor(beanProcessorClass);

		log.info("BeanPreProcessor registration took {} ms", timer.finish().getExecutionMillis());
	}

}
