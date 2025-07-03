package com.kaba4cow.difuse.core.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.application.ShutdownHook;
import com.kaba4cow.difuse.core.annotation.application.CoreComponent;
import com.kaba4cow.difuse.core.annotation.application.CoreDependency;
import com.kaba4cow.difuse.core.scope.handler.ScopeHandler;
import com.kaba4cow.difuse.core.scope.support.ScopeHandlerRegistry;
import com.kaba4cow.difuse.core.util.ExecutionTimer;

@CoreComponent
public class BeanDestroyer {

	private static final Logger log = LoggerFactory.getLogger("BeanDestroyer");

	@CoreDependency
	private ScopeHandlerRegistry scopeHandlerRegistry;

	@ShutdownHook
	public void destroyAllBeans() {
		log.info("Destroying beans...");
		ExecutionTimer timer = new ExecutionTimer().start();

		long totalBeansDestroyed = scopeHandlerRegistry.getAllScopeHandlers().stream()//
				.mapToLong(ScopeHandler::destroyBeans)//
				.sum();

		log.info("Bean destroying took {} ms", timer.finish().getExecutionMillis());
		log.debug("Total beans destroyed: {}", totalBeansDestroyed);
	}

}
