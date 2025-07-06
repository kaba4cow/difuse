package com.kaba4cow.difuse.aspects.bean.preprocessor;

import com.kaba4cow.difuse.aspects.advisor.support.AdvisorFactory;
import com.kaba4cow.difuse.aspects.annotation.Aspect;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.bean.processor.pre.BeanPreProcessor;
import com.kaba4cow.difuse.core.bean.source.BeanSource;
import com.kaba4cow.difuse.core.bean.source.impl.ClassBeanSource;

public class AspectBeanPreProcessor implements BeanPreProcessor {

	@Provided
	private AdvisorFactory advisorFactory;

	@Override
	public boolean process(BeanSource<?> beanSource) {
		if (beanSource instanceof ClassBeanSource && beanSource.getSourceElement().isAnnotationPresent(Aspect.class)) {
			advisorFactory.createAdvisor((ClassBeanSource) beanSource);
			return false;
		} else
			return true;
	}

}
