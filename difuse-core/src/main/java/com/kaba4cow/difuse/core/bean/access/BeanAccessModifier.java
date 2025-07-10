package com.kaba4cow.difuse.core.bean.access;

import java.lang.annotation.Annotation;

public interface BeanAccessModifier<T extends Annotation> {

	boolean isApplicable(T annotation);

}
