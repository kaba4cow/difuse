package com.kaba4cow.difuse.core.bean.access.restrictor.support;

import java.lang.annotation.Annotation;

import com.kaba4cow.difuse.core.annotation.access.restrictor.AccessRestrictor;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.bean.access.restrictor.BeanAccessRestrictor;
import com.kaba4cow.difuse.core.bean.access.restrictor.BeanAccessRestrictorException;
import com.kaba4cow.difuse.core.system.bean.SystemBeanInjector;

@SystemBean
public class BeanAccessRestrictorFactory {

	@Provided
	private SystemBeanInjector beanInjector;

	public BeanAccessRestrictor<?> createRestrictor(Class<? extends Annotation> annotation) {
		try {
			if (!annotation.isAnnotationPresent(AccessRestrictor.class))
				throw new BeanAccessRestrictorException(
						String.format("Not an AccessRestrictor annotation: %s", annotation.getName()));
			AccessRestrictor metaAnnotation = annotation.getAnnotation(AccessRestrictor.class);
			Class<? extends BeanAccessRestrictor<?>> type = metaAnnotation.value();
			return beanInjector.createInstance(type);
		} catch (Exception exception) {
			throw new BeanAccessRestrictorException(
					String.format("Could not create BeanAccessRestrictor for annotation %s", annotation.getName()), exception);
		}
	}

}
