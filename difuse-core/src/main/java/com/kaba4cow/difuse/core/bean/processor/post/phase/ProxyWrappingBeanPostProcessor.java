package com.kaba4cow.difuse.core.bean.processor.post.phase;

import com.kaba4cow.difuse.core.bean.processor.post.BeanLifecyclePhase;
import com.kaba4cow.difuse.core.bean.processor.post.BeanPostProcessor;

public abstract class ProxyWrappingBeanPostProcessor implements BeanPostProcessor {

	@Override
	public final BeanLifecyclePhase getLifecyclePhase() {
		return BeanLifecyclePhase.PROXY_WRAP;
	}

}
