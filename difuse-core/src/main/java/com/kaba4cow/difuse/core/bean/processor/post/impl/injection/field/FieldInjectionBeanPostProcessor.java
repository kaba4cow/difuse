package com.kaba4cow.difuse.core.bean.processor.post.impl.injection.field;

import com.kaba4cow.difuse.core.bean.processor.post.BeanLifecyclePhase;
import com.kaba4cow.difuse.core.bean.processor.post.BeanPostProcessor;

public abstract class FieldInjectionBeanPostProcessor implements BeanPostProcessor {

	@Override
	public final BeanLifecyclePhase getLifecyclePhase() {
		return BeanLifecyclePhase.FIELD_INJECTION;
	}

}
