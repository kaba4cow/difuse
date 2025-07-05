package com.kaba4cow.difuse.core.bean.preprocessor.support;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.bean.preprocessor.BeanPreProcessor;
import com.kaba4cow.difuse.core.system.PackageScannerPool;
import com.kaba4cow.difuse.core.util.LoggingTimer;
import com.kaba4cow.difuse.core.util.reflections.PackageScanner;

@SystemBean
public class BeanPreProcessorRegistrar {

	private static final Logger log = LoggerFactory.getLogger("BeanPreProcessorRegistrar");

	@Provided
	private PackageScannerPool packageScannerPool;

	@Provided
	private BeanPreProcessorRegistry beanPreProcessorRegistry;

	public void registerBeanPreProcessors(Class<?> sourceClass) {
		try (LoggingTimer timer = new LoggingTimer(log, "Registering BeanPreProcessors for {}...", sourceClass)) {
			PackageScanner packageScanner = packageScannerPool.getPackageScanner(sourceClass);
			Set<Class<? extends BeanPreProcessor>> beanProcessorClasses = packageScanner
					.searchClassesOf(BeanPreProcessor.class);
			for (Class<? extends BeanPreProcessor> beanProcessorClass : beanProcessorClasses)
				beanPreProcessorRegistry.registerBeanPreProcessor(beanProcessorClass);
		}
	}

}
