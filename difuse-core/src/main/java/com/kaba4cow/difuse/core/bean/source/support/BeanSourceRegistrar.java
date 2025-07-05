package com.kaba4cow.difuse.core.bean.source.support;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.bean.Bean;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.annotation.system.SystemDependency;
import com.kaba4cow.difuse.core.context.source.ContextSource;
import com.kaba4cow.difuse.core.system.PackageScannerPool;
import com.kaba4cow.difuse.core.util.ExecutionTimer;
import com.kaba4cow.difuse.core.util.reflections.PackageScanner;

@SystemBean
public class BeanSourceRegistrar {

	private static final Logger log = LoggerFactory.getLogger("BeanSourceRegistrar");

	@SystemDependency
	private PackageScannerPool packageScannerPool;

	@SystemDependency
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
