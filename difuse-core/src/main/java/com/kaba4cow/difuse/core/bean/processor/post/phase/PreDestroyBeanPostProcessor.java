package com.kaba4cow.difuse.core.bean.processor.post.phase;

import com.kaba4cow.difuse.core.bean.processor.post.BeanLifecyclePhase;
import com.kaba4cow.difuse.core.bean.processor.post.BeanPostProcessor;

public abstract class PreDestroyBeanPostProcessor implements BeanPostProcessor {

	@Override
	public final BeanLifecyclePhase getLifecyclePhase() {
		return BeanLifecyclePhase.PRE_DESTROY;
	}

}
