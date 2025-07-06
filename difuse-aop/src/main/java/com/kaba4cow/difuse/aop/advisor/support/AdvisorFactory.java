package com.kaba4cow.difuse.aop.advisor.support;

import com.kaba4cow.difuse.aop.advisor.Advisor;
import com.kaba4cow.difuse.aop.advisor.AdvisorException;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.Accessible;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.bean.source.BeanSource;
import com.kaba4cow.difuse.core.system.bean.SystemBeanInjector;
import com.kaba4cow.difuse.core.util.reflections.ConstructorScanner;

@Accessible
@SystemBean
public class AdvisorFactory {

	@Provided
	private AdvisorRegistry registry;

	@Provided
	private SystemBeanInjector beanInjector;

	public void createAdvisor(BeanSource<?> beanSource) {
		Object aspect = createAspect(beanSource.getDeclaringClass());
		Advisor advisor = new Advisor(aspect, beanSource);
		registry.registerAdvisor(advisor);
	}

	private Object createAspect(Class<?> type) {
		try {
			Object instance = ConstructorScanner.of(type).findNoArgsConstructor();
			beanInjector.injectDependencies(instance);
			return instance;
		} catch (Exception exception) {
			throw new AdvisorException(String.format("Could not create aspect instance of type %s", type.getName()), exception);
		}
	}

}
