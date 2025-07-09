package com.kaba4cow.difuse.core.bean.processor.pre;

import com.kaba4cow.difuse.core.bean.processor.BeanProcessor;
import com.kaba4cow.difuse.core.bean.source.BeanSource;

public interface BeanPreProcessor extends BeanProcessor {

	<T extends BeanSource<?>> T process(T beanSource);

}
