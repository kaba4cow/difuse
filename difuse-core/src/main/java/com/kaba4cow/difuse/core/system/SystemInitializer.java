package com.kaba4cow.difuse.core.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.context.support.ContextInitializer;
import com.kaba4cow.difuse.core.context.support.ContextRegistry;
import com.kaba4cow.difuse.core.context.support.ContextScanner;
import com.kaba4cow.difuse.core.system.bean.SystemBeanInitializer;
import com.kaba4cow.difuse.core.system.bean.SystemBeanInjector;
import com.kaba4cow.difuse.core.system.bean.SystemBeanRegistrar;
import com.kaba4cow.difuse.core.system.bean.registry.impl.AccessibleSystemBeanRegistry;
import com.kaba4cow.difuse.core.system.bean.registry.impl.InternalSystemBeanRegistry;
import com.kaba4cow.difuse.core.system.shutdownhook.SystemShutdownHookDispatcher;
import com.kaba4cow.difuse.core.system.shutdownhook.SystemShutdownHookRegistrar;
import com.kaba4cow.difuse.core.system.shutdownhook.SystemShutdownHookRegistry;
import com.kaba4cow.difuse.core.util.LoggingTimer;

public class SystemInitializer {

	private static final Logger log = LoggerFactory.getLogger("SystemInitializer");

	public SystemLauncher initialize(SystemParameters systemParameters) {
		try (LoggingTimer timer = new LoggingTimer(log, "Initializing system...")) {
			ContextScanner contextScanner = new ContextScanner();

			ContextInitializer contextInitializer = new ContextInitializer(systemParameters);
			ContextRegistry contextRegistry = contextInitializer.initializeContexts();

			InternalSystemBeanRegistry internalBeanRegistry = new InternalSystemBeanRegistry();
			AccessibleSystemBeanRegistry accessibleBeanRegistry = new AccessibleSystemBeanRegistry();

			SystemShutdownHookRegistry shutdownHookRegistry = new SystemShutdownHookRegistry();
			SystemShutdownHookRegistrar shutdownHookRegistrar = new SystemShutdownHookRegistrar(shutdownHookRegistry);
			SystemShutdownHookDispatcher shutdownHookDispatcher = new SystemShutdownHookDispatcher(shutdownHookRegistry);

			SystemBeanRegistrar beanRegistrar = new SystemBeanRegistrar(//
					contextScanner, //
					contextRegistry, //
					internalBeanRegistry, //
					shutdownHookRegistrar);

			SystemBeanInjector beanInjector = new SystemBeanInjector(internalBeanRegistry);

			beanRegistrar.registerBean(systemParameters);
			beanRegistrar.registerBean(contextScanner);
			beanRegistrar.registerBean(contextRegistry);
			beanRegistrar.registerBean(internalBeanRegistry);
			beanRegistrar.registerBean(accessibleBeanRegistry);
			beanRegistrar.registerBean(shutdownHookDispatcher);
			beanRegistrar.registerBean(beanInjector);
			SystemLauncher launcher = beanRegistrar.registerBean(SystemLauncher.class);

			SystemBeanInitializer beanInitializer = new SystemBeanInitializer(internalBeanRegistry, beanInjector);

			beanRegistrar.registerBeans();
			beanInitializer.initializeBeans();
			return launcher;
		}
	}

}
