package com.kaba4cow.difuse.core.bean.source.support;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.bean.Bean;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.context.ContextScanner;
import com.kaba4cow.difuse.core.context.source.ContextSource;
import com.kaba4cow.difuse.core.util.LoggingTimer;
import com.kaba4cow.difuse.core.util.reflections.PackageScanner;

@SystemBean
public class BeanSourceRegistrar {

	private static final Logger log = LoggerFactory.getLogger("BeanSourceRegistrar");

	@Provided
	private ContextScanner contextScanner;

	@Provided
	private BeanSourceFactory beanSourceFactory;

	public void registerBeanSources(ContextSource contextSource) {
		try (LoggingTimer timer = new LoggingTimer(log, "Registering BeanSources for {}...", contextSource)) {
			PackageScanner packageScanner = contextScanner.getScanner(contextSource.getSourceClass());
			Set<Class<?>> beanClasses = packageScanner.searchClassesAnnotatedWith(Bean.class);
			for (Class<?> beanClass : beanClasses)
				beanSourceFactory.createClassBeanSource(contextSource, beanClass);
		}
	}

}
