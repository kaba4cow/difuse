package com.kaba4cow.difuse.core.bean.provider.support;

import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.bean.processor.post.support.BeanPostProcessorChain;
import com.kaba4cow.difuse.core.bean.provider.impl.ClassBeanProvider;
import com.kaba4cow.difuse.core.bean.provider.impl.MethodBeanProvider;
import com.kaba4cow.difuse.core.bean.source.impl.ClassBeanSource;
import com.kaba4cow.difuse.core.bean.source.impl.MethodBeanSource;
import com.kaba4cow.difuse.core.dependency.provider.impl.GlobalDependencyProvider;

@SystemBean
public class BeanProviderFactory {

	@Provided
	private BeanProviderRegistry providerRegistry;

	@Provided
	private BeanPostProcessorChain postProcessorChain;

	@Provided
	private GlobalDependencyProvider dependencyProvider;

	public void createClassBeanProvider(ClassBeanSource classBeanSource) {
		ClassBeanProvider classBeanProvider = new ClassBeanProvider(classBeanSource, dependencyProvider, postProcessorChain);
		providerRegistry.register(classBeanProvider);
		createMethodBeanProviders(classBeanSource, classBeanProvider);
	}

	private void createMethodBeanProviders(ClassBeanSource ownerBeanSource, ClassBeanProvider ownerBeanProvider) {
		for (MethodBeanSource childBeanSource : ownerBeanSource.getChildBeanSources())
			createMethodBeanProvider(childBeanSource, ownerBeanProvider);
	}

	private void createMethodBeanProvider(MethodBeanSource methodBeanSource, ClassBeanProvider ownerBeanProvider) {
		MethodBeanProvider beanProvider = new MethodBeanProvider(methodBeanSource, dependencyProvider, ownerBeanProvider);
		providerRegistry.register(beanProvider);
	}

}
