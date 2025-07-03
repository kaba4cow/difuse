package com.kaba4cow.difuse.core.bean.provider.impl;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.kaba4cow.difuse.core.DifuseException;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.bean.processor.support.GlobalBeanProcessor;
import com.kaba4cow.difuse.core.bean.provider.BeanProvider;
import com.kaba4cow.difuse.core.bean.source.impl.ClassBeanSource;
import com.kaba4cow.difuse.core.dependency.provider.DependencyProvider;
import com.kaba4cow.difuse.core.dependency.provider.DependencyProviderSession;

public class ClassBeanProvider extends BeanProvider<ClassBeanSource> {

	private final GlobalBeanProcessor globalBeanProcessor;

	private final Constructor<?> targetConstructor;

	public ClassBeanProvider(//
			ClassBeanSource beanSource, //
			DependencyProvider dependencyProvider, //
			GlobalBeanProcessor globalBeanProcessor) {
		super(beanSource, dependencyProvider);
		this.globalBeanProcessor = globalBeanProcessor;
		this.targetConstructor = findTargetConstructor();
	}

	@Override
	protected Object createBean(DependencyProviderSession session) {
		try {
			Object bean = createBeanInstance(session);
			return globalBeanProcessor.process(bean, this, session);
		} catch (Exception exception) {
			throw new DifuseException(String.format("Could not create class bean of type %s", getBeanSource().getBeanClass()),
					exception);
		}
	}

	private Object createBeanInstance(DependencyProviderSession session) {
		try {
			return targetConstructor.newInstance(session.provideDependencies(targetConstructor));
		} catch (Exception exception) {
			throw new DifuseException(String.format("Could not instantiate bean of type %s", getBeanSource().getBeanClass()),
					exception);
		}
	}

	private Constructor<?> findTargetConstructor() {
		try {
			Constructor<?>[] constructors = getBeanSource().getBeanClass().getConstructors();
			if (constructors.length == 0)
				throw new DifuseException("No public constructors found");
			else if (constructors.length == 1)
				return constructors[0];
			else {
				List<Constructor<?>> providedConstructors = Arrays.stream(constructors)//
						.filter(constructor -> constructor.isAnnotationPresent(Provided.class))//
						.collect(Collectors.toList());
				if (providedConstructors.isEmpty())
					throw new DifuseException("No @Provided constructor found");
				else if (providedConstructors.size() > 1)
					throw new DifuseException("Multiple @Provided constructors found");
				else
					return providedConstructors.get(0);
			}
		} catch (Exception exception) {
			throw new DifuseException("No target constructor found", exception);
		}
	}

}
