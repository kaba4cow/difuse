package com.kaba4cow.difuse.core.bean.source.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.context.Context;
import com.kaba4cow.difuse.core.context.support.ContextRegistry;
import com.kaba4cow.difuse.core.util.LoggingTimer;

@SystemBean
public class BeanSourceInitializer {

	private static final Logger log = LoggerFactory.getLogger("BeanSourceInitializer");

	@Provided
	private ContextRegistry contextRegistry;

	@Provided
	private BeanSourceRegistrar beanSourceRegistrar;

	public void initializeBeanSources() {
		try (LoggingTimer timer = new LoggingTimer(log, "Initializing BeanSources...")) {
			for (Context context : contextRegistry.getContexts())
				beanSourceRegistrar.registerBeanSources(context);
		}
	}

}
