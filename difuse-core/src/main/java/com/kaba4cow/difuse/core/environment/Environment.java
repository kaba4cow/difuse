package com.kaba4cow.difuse.core.environment;

import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;
import java.util.TreeSet;

import com.kaba4cow.difuse.core.annotation.system.Accessible;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.environment.config.source.ConfigSource;

@Accessible
@SystemBean
public class Environment {

	private final Deque<ConfigSource> sources = new LinkedList<>();

	private final Collection<String> profiles = new TreeSet<>();

	public Environment() {}

	public String getProperty(String key) {
		for (ConfigSource source : sources) {
			String value = source.getProperty(key);
			if (Objects.nonNull(value))
				return value;
		}
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

	public Collection<ConfigSource> getPropertySources() {
		return Collections.unmodifiableCollection(sources);
	}

	public void includeProfile(String profile) {
		profiles.add(profile);
	}

	public boolean hasProfile(String profile) {
		return profiles.contains(profile);
	}

	public Collection<String> getProfiles() {
		return Collections.unmodifiableCollection(profiles);
	}

}
