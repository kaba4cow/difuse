package com.kaba4cow.difuse.core.bean.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.annotation.system.SystemShutdownHook;
import com.kaba4cow.difuse.core.scope.Scope;
import com.kaba4cow.difuse.core.scope.support.ScopeRegistry;
import com.kaba4cow.difuse.core.util.LoggingTimer;

@SystemBean
public class BeanDestroyer {

	private static final Logger log = LoggerFactory.getLogger("BeanDestroyer");

	@Provided
	private ScopeRegistry scopeRegistry;

	@SystemShutdownHook
	public void destroyAllBeans() {
		try (LoggingTimer timer = new LoggingTimer(log, "Destroying beans...")) {
			long totalBeansDestroyed = scopeRegistry.getAllScopes().stream()//
					.mapToLong(Scope::destroyBeans)//
					.sum();
			log.debug("Total beans destroyed: {}", totalBeansDestroyed);
		}
	}

}
