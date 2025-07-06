package com.kaba4cow.difuse.aop.bean.preprocessor;

import com.kaba4cow.difuse.aop.advisor.support.AdvisorFactory;
import com.kaba4cow.difuse.aop.annotation.Aspect;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.bean.preprocessor.BeanPreProcessor;
import com.kaba4cow.difuse.core.bean.source.BeanSource;
import com.kaba4cow.difuse.core.bean.source.impl.ClassBeanSource;

public class AspectBeanPreProcessor implements BeanPreProcessor {

	@Provided
	private AdvisorFactory advisorFactory;

	@Override
	public boolean process(BeanSource<?> beanSource) {
		if (beanSource instanceof ClassBeanSource && beanSource.getSourceElement().isAnnotationPresent(Aspect.class)) {
			advisorFactory.createAdvisor(beanSource);
			return false;
		}
		return true;
	}

}
