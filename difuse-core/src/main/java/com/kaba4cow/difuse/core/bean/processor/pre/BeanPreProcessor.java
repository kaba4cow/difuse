package com.kaba4cow.difuse.core.bean.processor.pre;

import com.kaba4cow.difuse.core.bean.source.BeanSource;

public interface BeanPreProcessor {

	boolean process(BeanSource<?> beanSource);

}
