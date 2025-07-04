package com.kaba4cow.difuse.core.bean.postprocessor.support;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.bean.postprocessor.BeanPostProcessor;
import com.kaba4cow.difuse.core.system.PackageScannerPool;
import com.kaba4cow.difuse.core.util.LoggingTimer;
import com.kaba4cow.difuse.core.util.reflections.PackageScanner;

@SystemBean
public class BeanPostProcessorRegistrar {

	private static final Logger log = LoggerFactory.getLogger("BeanPostProcessorRegistrar");

	@Provided
	private PackageScannerPool packageScannerPool;

	@Provided
	private BeanPostProcessorRegistry beanPostProcessorRegistry;

	public void registerBeanPostProcessors(Class<?> sourceClass) {
		try (LoggingTimer timer = new LoggingTimer(log, "Registering BeanPostProcessors for {}...", sourceClass)) {
			PackageScanner packageScanner = packageScannerPool.getPackageScanner(sourceClass);
			Set<Class<? extends BeanPostProcessor>> beanProcessorClasses = packageScanner
					.searchClassesOf(BeanPostProcessor.class);
			for (Class<? extends BeanPostProcessor> beanProcessorClass : beanProcessorClasses)
				beanPostProcessorRegistry.registerBeanPostProcessor(beanProcessorClass);
		}
	}

}
