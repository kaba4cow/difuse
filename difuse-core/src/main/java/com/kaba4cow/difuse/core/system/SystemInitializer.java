package com.kaba4cow.difuse.core.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.system.bean.SystemBeanInitializer;
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
			SystemShutdownHookRegistry shutdownHookRegistry = new SystemShutdownHookRegistry();
			SystemShutdownHookRegistrar shutdownHookRegistrar = new SystemShutdownHookRegistrar(shutdownHookRegistry);
			SystemShutdownHookDispatcher shutdownHookDispatcher = new SystemShutdownHookDispatcher(shutdownHookRegistry);

			InternalSystemBeanRegistry internalBeanRegistry = new InternalSystemBeanRegistry();
			AccessibleSystemBeanRegistry accessibleBeanRegistry = new AccessibleSystemBeanRegistry();

			SystemBeanRegistrar beanRegistrar = new SystemBeanRegistrar(internalBeanRegistry, shutdownHookRegistrar);

			SystemBeanInitializer beanInitializer = new SystemBeanInitializer(internalBeanRegistry);

			beanRegistrar.registerBean(systemParameters);
			beanRegistrar.registerBean(internalBeanRegistry);
			beanRegistrar.registerBean(accessibleBeanRegistry);
			beanRegistrar.registerBean(shutdownHookDispatcher);
			SystemLauncher launcher = beanRegistrar.registerBean(SystemLauncher.class);

			beanRegistrar.registerBeans();
			beanInitializer.initializeBeans();
			return launcher;
		}
	}

}
