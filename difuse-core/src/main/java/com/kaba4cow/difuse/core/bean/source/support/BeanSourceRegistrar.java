package com.kaba4cow.difuse.core.bean.source.support;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.application.CoreComponent;
import com.kaba4cow.difuse.core.annotation.application.CoreDependency;
import com.kaba4cow.difuse.core.annotation.bean.Bean;
import com.kaba4cow.difuse.core.application.PackageScannerPool;
import com.kaba4cow.difuse.core.context.source.ContextSource;
import com.kaba4cow.difuse.core.util.ExecutionTimer;
import com.kaba4cow.difuse.core.util.reflections.PackageScanner;

@CoreComponent
public class BeanSourceRegistrar {

	private static final Logger log = LoggerFactory.getLogger("BeanSourceRegistrar");

	@CoreDependency
	private PackageScannerPool packageScannerPool;

	@CoreDependency
	private BeanSourceFactory beanSourceFactory;

	public void registerBeanSources(ContextSource contextSource) {
		log.info("Registering BeanSources for {}...", contextSource);
		ExecutionTimer timer = new ExecutionTimer().start();

		PackageScanner packageScanner = packageScannerPool.getPackageScanner(contextSource.getSourceClass());
		Set<Class<?>> beanClasses = packageScanner.searchClassesAnnotatedWith(Bean.class);
		for (Class<?> beanClass : beanClasses)
			beanSourceFactory.createClassBeanSource(contextSource, beanClass);

		log.info("BeanSource registration took {} ms", timer.finish().getExecutionMillis());
	}

}
