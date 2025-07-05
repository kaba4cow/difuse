package com.kaba4cow.difuse.core.bean.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.annotation.system.SystemDependency;
import com.kaba4cow.difuse.core.annotation.system.SystemShutdownHook;
import com.kaba4cow.difuse.core.scope.Scope;
import com.kaba4cow.difuse.core.scope.support.ScopeRegistry;
import com.kaba4cow.difuse.core.util.ExecutionTimer;

@SystemBean
public class BeanDestroyer {

	private static final Logger log = LoggerFactory.getLogger("BeanDestroyer");

	@SystemDependency
	private ScopeRegistry scopeRegistry;

	@SystemShutdownHook
	public void destroyAllBeans() {
		log.info("Destroying beans...");
		ExecutionTimer timer = new ExecutionTimer().start();

		long totalBeansDestroyed = scopeRegistry.getAllScopes().stream()//
				.mapToLong(Scope::destroyBeans)//
				.sum();

		log.info("Bean destroying took {} ms", timer.finish().getExecutionMillis());
		log.debug("Total beans destroyed: {}", totalBeansDestroyed);
	}

}
