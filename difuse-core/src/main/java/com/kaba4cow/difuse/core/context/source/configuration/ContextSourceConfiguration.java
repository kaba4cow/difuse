package com.kaba4cow.difuse.core.context.source.configuration;

import java.util.Set;

public class ContextSourceConfiguration {

	private final Set<String> includedConfigs;

	private final Set<String> includedProfiles;

	private final Set<Class<?>> includedContexts;

	public ContextSourceConfiguration(Set<String> includedConfigs, Set<String> includedProfiles,
			Set<Class<?>> includedContexts) {
		this.includedConfigs = includedConfigs;
		this.includedProfiles = includedProfiles;
		this.includedContexts = includedContexts;
	}

	public Set<String> getIncludedConfigs() {
		return includedConfigs;
	}

	public Set<String> getIncludedProfiles() {
		return includedProfiles;
	}

	public Set<Class<?>> getIncludedContexts() {
		return includedContexts;
	}

	@Override
	public String toString() {
		return String.format("ContextSourceConfiguration [includedConfigs=%s, includedProfiles=%s, includedContexts=%s]",
				includedConfigs, includedProfiles, includedContexts);
	}

}
