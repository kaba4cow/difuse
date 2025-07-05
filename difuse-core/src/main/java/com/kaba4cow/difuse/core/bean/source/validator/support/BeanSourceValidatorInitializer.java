package com.kaba4cow.difuse.core.bean.source.validator.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.DifuseApplication;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.context.source.ContextSource;
import com.kaba4cow.difuse.core.context.source.support.ContextSourceRegistry;
import com.kaba4cow.difuse.core.util.LoggingTimer;

@SystemBean
public class BeanSourceValidatorInitializer {

	private static final Logger log = LoggerFactory.getLogger("BeanSourceValidatorInitializer");

	@Provided
	private ContextSourceRegistry contextSourceRegistry;

	@Provided
	private BeanSourceValidatorRegistrar beanSourceValidatorRegistrar;

	public void initializeBeanSourceValidators() {
		try (LoggingTimer timer = new LoggingTimer(log, "Initializing BeanSourceValidators...")) {
			beanSourceValidatorRegistrar.registerBeanSourceValidators(DifuseApplication.class);
			for (ContextSource contextSource : contextSourceRegistry.getContextSources())
				beanSourceValidatorRegistrar.registerBeanSourceValidators(contextSource.getSourceClass());
		}
	}

}
