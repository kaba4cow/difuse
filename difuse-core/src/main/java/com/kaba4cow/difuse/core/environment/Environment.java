package com.kaba4cow.difuse.core.environment;

import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import com.kaba4cow.difuse.core.annotation.system.Accessible;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.config.source.ConfigSource;

@Accessible
@SystemBean
public class Environment {

	private final Deque<ConfigSource> sources = new LinkedList<>();

	private final Set<String> profiles = new HashSet<>();

	private final Set<String> configs = new HashSet<>();

	public Object getProperty(String key) {
		for (ConfigSource source : sources)
			if (source.hasProperty(key))
				return source.getProperty(key);
		return null;
	}

	public boolean hasProperty(String key) {
		for (ConfigSource source : sources)
			if (source.hasProperty(key))
				return true;
		return false;
	}

	public void addPropertySource(ConfigSource source) {
		sources.addFirst(source);
	}

	public Collection<ConfigSource> getConfigSources() {
		return Collections.unmodifiableCollection(sources);
	}

	public void includeProfile(String profile) {
		profiles.add(profile);
	}

	public boolean hasProfile(String profile) {
		return profiles.contains(profile);
	}

	public Set<String> getProfiles() {
		return Collections.unmodifiableSet(profiles);
	}

	public void includeConfig(String config) {
		configs.add(config);
	}

	public boolean hasConfig(String config) {
		return configs.contains(config);
	}

	public Set<String> getConfigs() {
		return Collections.unmodifiableSet(configs);
	}

}
