package com.kaba4cow.difuse.core.bean.postprocessor.support;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.annotation.system.SystemDependency;
import com.kaba4cow.difuse.core.bean.postprocessor.BeanPostProcessor;
import com.kaba4cow.difuse.core.system.PackageScannerPool;
import com.kaba4cow.difuse.core.util.ExecutionTimer;
import com.kaba4cow.difuse.core.util.reflections.PackageScanner;

@SystemBean
public class BeanPostProcessorRegistrar {

	private static final Logger log = LoggerFactory.getLogger("BeanPostProcessorRegistrar");

	@SystemDependency
	private PackageScannerPool packageScannerPool;

	@SystemDependency
	private BeanPostProcessorRegistry beanPostProcessorRegistry;

	public void registerBeanPostProcessors(Class<?> sourceClass) {
		log.info("Registering BeanPostProcessors for {}...", sourceClass);
		ExecutionTimer timer = new ExecutionTimer().start();

		PackageScanner packageScanner = packageScannerPool.getPackageScanner(sourceClass);
		Set<Class<? extends BeanPostProcessor>> beanProcessorClasses = packageScanner.searchClassesOf(BeanPostProcessor.class);
		for (Class<? extends BeanPostProcessor> beanProcessorClass : beanProcessorClasses)
			beanPostProcessorRegistry.registerBeanPostProcessor(beanProcessorClass);

		log.info("BeanPostProcessor registration took {} ms", timer.finish().getExecutionMillis());
	}

}
