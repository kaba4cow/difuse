package com.kaba4cow.difuse.core.bean.source.support;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.stream.Collectors;

import com.kaba4cow.difuse.core.annotation.bean.Bean;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.bean.preprocessor.support.GlobalBeanPreProcessor;
import com.kaba4cow.difuse.core.bean.protector.BeanProtector;
import com.kaba4cow.difuse.core.bean.protector.BeanProtectorFactory;
import com.kaba4cow.difuse.core.bean.source.impl.ClassBeanSource;
import com.kaba4cow.difuse.core.bean.source.impl.MethodBeanSource;
import com.kaba4cow.difuse.core.context.source.ContextSource;
import com.kaba4cow.difuse.core.scope.support.ScopeRegistry;
import com.kaba4cow.difuse.core.util.reflections.MethodScanner;

@SystemBean
public class BeanSourceFactory {

	@Provided
	private BeanSourceRegistry beanSourceRegistry;

	@Provided
	private ScopeRegistry scopeRegistry;

	@Provided
	private BeanProtectorFactory beanProtectorFactory;

	@Provided
	private GlobalBeanPreProcessor globalBeanPreProcessor;

	public void createClassBeanSource(ContextSource contextSource, Class<?> beanClass) {
		BeanProtector classBeanProtector = beanProtectorFactory.createBeanProtector(beanClass);
		ClassBeanSource classBeanSource = new ClassBeanSource(//
				contextSource, //
				beanClass, //
				classBeanProtector, //
				scopeRegistry);
		if (globalBeanPreProcessor.process(classBeanSource)) {
			beanSourceRegistry.register(classBeanSource);
			createMethodBeanSources(contextSource, classBeanSource);
		}
	}

	private void createMethodBeanSources(ContextSource contextSource, ClassBeanSource ownerBeanSource) {
		for (Method beanMethod : findBeanMethods(ownerBeanSource.getDeclaringClass()))
			createMethodBeanSource(contextSource, beanMethod, ownerBeanSource);
	}

	private void createMethodBeanSource(ContextSource contextSource, Method beanMethod, ClassBeanSource ownerBeanSource) {
		BeanProtector beanProtector = beanProtectorFactory.createBeanProtector(ownerBeanSource.getBeanClass(), beanMethod);
		MethodBeanSource methodBeanSource = new MethodBeanSource(//
				contextSource, //
				beanMethod, //
				beanProtector, //
				scopeRegistry, //
				ownerBeanSource.getDeclaringClass());
		if (globalBeanPreProcessor.process(methodBeanSource)) {
			ownerBeanSource.addChildBeanSource(methodBeanSource);
			beanSourceRegistry.register(methodBeanSource);
		}
	}

	private Set<Method> findBeanMethods(Class<?> sourceClass) {
		return MethodScanner.of(sourceClass).findMethods().stream()//
				.filter(method -> !Modifier.isStatic(method.getModifiers()))//
				.filter(method -> method.isAnnotationPresent(Bean.class))//
				.collect(Collectors.toSet());
	}

}
