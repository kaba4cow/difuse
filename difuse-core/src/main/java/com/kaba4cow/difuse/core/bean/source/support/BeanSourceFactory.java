package com.kaba4cow.difuse.core.bean.source.support;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.stream.Collectors;

import com.kaba4cow.difuse.core.annotation.bean.Bean;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.bean.processor.pre.support.GlobalBeanPreProcessor;
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
		BeanProtector beanProtector = beanProtectorFactory.createBeanProtector();
		ClassBeanSource classBeanSource = new ClassBeanSource(//
				contextSource, //
				beanClass, //
				beanProtector, //
				scopeRegistry);
		globalBeanPreProcessor.process(classBeanSource)//
				.ifPresent(this::registerClassBeanSource);
	}

	private void registerClassBeanSource(ClassBeanSource classBeanSource) {
		beanSourceRegistry.register(classBeanSource);
		createMethodBeanSources(classBeanSource.getContextSource(), classBeanSource);
	}

	private void createMethodBeanSources(ContextSource contextSource, ClassBeanSource parentBeanSource) {
		for (Method beanMethod : findBeanMethods(parentBeanSource))
			createMethodBeanSource(contextSource, beanMethod, parentBeanSource);
	}

	private void createMethodBeanSource(ContextSource contextSource, Method beanMethod, ClassBeanSource parentBeanSource) {
		BeanProtector beanProtector = beanProtectorFactory.createBeanProtector();
		MethodBeanSource methodBeanSource = new MethodBeanSource(//
				contextSource, //
				beanMethod, //
				beanProtector, //
				scopeRegistry, //
				parentBeanSource);
		globalBeanPreProcessor.process(methodBeanSource)//
				.ifPresent(this::registerMethodBeanSource);
	}

	private void registerMethodBeanSource(MethodBeanSource methodBeanSource) {
		methodBeanSource.getParentBeanSource().addChildBeanSource(methodBeanSource);
		beanSourceRegistry.register(methodBeanSource);
	}

	private Set<Method> findBeanMethods(ClassBeanSource classBeanSource) {
		return MethodScanner.of(classBeanSource.getDeclaringClass()).findMethods().stream()//
				.filter(method -> !Modifier.isStatic(method.getModifiers()))//
				.filter(method -> method.isAnnotationPresent(Bean.class))//
				.collect(Collectors.toSet());
	}

}
