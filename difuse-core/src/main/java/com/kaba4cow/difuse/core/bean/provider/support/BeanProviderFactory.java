package com.kaba4cow.difuse.core.bean.provider.support;

import com.kaba4cow.difuse.core.annotation.application.CoreComponent;
import com.kaba4cow.difuse.core.annotation.application.CoreDependency;
import com.kaba4cow.difuse.core.bean.processor.support.GlobalBeanProcessor;
import com.kaba4cow.difuse.core.bean.provider.impl.ClassBeanProvider;
import com.kaba4cow.difuse.core.bean.provider.impl.MethodBeanProvider;
import com.kaba4cow.difuse.core.bean.source.impl.ClassBeanSource;
import com.kaba4cow.difuse.core.bean.source.impl.MethodBeanSource;
import com.kaba4cow.difuse.core.dependency.provider.impl.DelegatingDependencyProvider;

@CoreComponent
public class BeanProviderFactory {

	@CoreDependency
	private BeanProviderRegistry beanProviderRegistry;

	@CoreDependency
	private GlobalBeanProcessor globalBeanProcessor;

	@CoreDependency
	private DelegatingDependencyProvider dependencyProvider;

	public void createClassBeanProvider(ClassBeanSource classBeanSource) {
		ClassBeanProvider classBeanProvider = new ClassBeanProvider(classBeanSource, dependencyProvider, globalBeanProcessor);
		beanProviderRegistry.register(classBeanProvider);
		createMethodBeanProviders(classBeanSource, classBeanProvider);
	}

	private void createMethodBeanProviders(ClassBeanSource ownerBeanSource, ClassBeanProvider ownerBeanProvider) {
		for (MethodBeanSource childBeanSource : ownerBeanSource.getChildBeanSources())
			createMethodBeanProvider(childBeanSource, ownerBeanProvider);
	}

	private void createMethodBeanProvider(MethodBeanSource methodBeanSource, ClassBeanProvider ownerBeanProvider) {
		MethodBeanProvider beanProvider = new MethodBeanProvider(methodBeanSource, dependencyProvider, ownerBeanProvider);
		beanProviderRegistry.register(beanProvider);
	}

}
