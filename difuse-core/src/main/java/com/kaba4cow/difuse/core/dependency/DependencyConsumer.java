package com.kaba4cow.difuse.core.dependency;

import com.kaba4cow.difuse.core.context.source.ContextSource;

public interface DependencyConsumer {

	ContextSource getContextSource();

	Class<?> getConsumerClass();

}
