package com.kaba4cow.difuse.core.context.source.configuration.builder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.kaba4cow.difuse.core.context.source.configuration.ContextConfiguration;

public class DefaultContextConfigurationBuilder implements ContextConfigurationBuilder {

	private final Set<String> includedConfigs = new HashSet<>();

	private final Set<String> includedProfiles = new HashSet<>();

	private final Set<Class<?>> includedContexts = new HashSet<>();

	public void includeConfig(String config) {
		includedConfigs.add(config);
	}

	public void includeConfigs(Collection<String> configs) {
		includedConfigs.addAll(configs);
	}

	public void includeProfile(String profile) {
		includedProfiles.add(profile);
	}

	public void includeProfiles(Collection<String> profiles) {
		includedProfiles.addAll(profiles);
	}

	public void includeContext(Class<?> sourceClass) {
		includedContexts.add(sourceClass);
	}

	public void includeContexts(Collection<Class<?>> sourceClasses) {
		includedContexts.addAll(sourceClasses);
	}

	@Override
	public ContextConfiguration build() {
		return new ContextConfiguration(includedConfigs, includedProfiles, includedContexts);
	}

}
