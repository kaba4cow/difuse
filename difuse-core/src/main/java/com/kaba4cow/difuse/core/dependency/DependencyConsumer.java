package com.kaba4cow.difuse.core.dependency;

import com.kaba4cow.difuse.core.context.Context;

public interface DependencyConsumer {

	Context getContext();

	Class<?> getConsumerClass();

}
