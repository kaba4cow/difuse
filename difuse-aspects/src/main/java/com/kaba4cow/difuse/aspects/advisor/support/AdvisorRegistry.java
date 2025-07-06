package com.kaba4cow.difuse.aspects.advisor.support;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.aspects.advisor.Advisor;
import com.kaba4cow.difuse.core.annotation.system.Accessible;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;

@Accessible
@SystemBean
public class AdvisorRegistry {

	private static final Logger log = LoggerFactory.getLogger("AdvisorRegistry");

	private final Set<Advisor> registry = ConcurrentHashMap.newKeySet();

	public void registerAdvisor(Advisor advisor) {
		registry.add(advisor);
		log.debug("Registered Advisor for {}", advisor.getBeanSource());
	}

	public Set<Advisor> getAdvisors() {
		return Collections.unmodifiableSet(registry);
	}

	public boolean hasAdvisors() {
		return !registry.isEmpty();
	}

}
