package com.kaba4cow.difuse.core.bean.source.support;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.bean.Bean;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.context.Context;
import com.kaba4cow.difuse.core.context.support.ContextScanner;
import com.kaba4cow.difuse.core.util.LoggingTimer;
import com.kaba4cow.difuse.core.util.reflections.PackageScanner;

@SystemBean
public class BeanSourceRegistrar {

	private static final Logger log = LoggerFactory.getLogger("BeanSourceRegistrar");

	@Provided
	private ContextScanner contextScanner;

	@Provided
	private BeanSourceFactory beanSourceFactory;

	public void registerBeanSources(Context context) {
		try (LoggingTimer timer = new LoggingTimer(log, "Registering BeanSources for {}...", context)) {
			PackageScanner packageScanner = contextScanner.getScanner(context);
			Set<Class<?>> beanClasses = packageScanner.searchClassesAnnotatedWith(Bean.class);
			for (Class<?> beanClass : beanClasses)
				beanSourceFactory.createClassBeanSource(context, beanClass);
		}
	}

}
