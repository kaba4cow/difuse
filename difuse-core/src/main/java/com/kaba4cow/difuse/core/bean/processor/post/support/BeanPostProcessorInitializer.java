package com.kaba4cow.difuse.core.bean.processor.post.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.DifuseApplication;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.context.Context;
import com.kaba4cow.difuse.core.context.support.ContextRegistry;
import com.kaba4cow.difuse.core.util.LoggingTimer;

@SystemBean
public class BeanPostProcessorInitializer {

	private static final Logger log = LoggerFactory.getLogger("BeanPostProcessorInitializer");

	@Provided
	private ContextRegistry contextRegistry;

	@Provided
	private BeanPostProcessorRegistrar beanPostProcessorRegistrar;

	public void initializeBeanPostProcessors() {
		try (LoggingTimer timer = new LoggingTimer(log, "Initializing BeanPostProcessors...")) {
			beanPostProcessorRegistrar.registerBeanPostProcessors(DifuseApplication.class);
			for (Context context : contextRegistry.getContexts())
				beanPostProcessorRegistrar.registerBeanPostProcessors(context.getSourceClass());
		}
	}

}
