package com.kaba4cow.difuse.core.bean.access.provider.support;

import java.lang.annotation.Annotation;

import com.kaba4cow.difuse.core.annotation.access.provider.AccessProvider;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.bean.access.provider.BeanAccessProvider;
import com.kaba4cow.difuse.core.bean.access.provider.BeanAccessProviderException;
import com.kaba4cow.difuse.core.system.bean.SystemBeanInjector;

@SystemBean
public class BeanAccessProviderFactory {

	@Provided
	private SystemBeanInjector beanInjector;

	public BeanAccessProvider<?> createProvider(Class<? extends Annotation> annotation) {
		try {
			if (!annotation.isAnnotationPresent(AccessProvider.class))
				throw new BeanAccessProviderException(
						String.format("Not an AccessProvider annotation: %s", annotation.getName()));
			AccessProvider metaAnnotation = annotation.getAnnotation(AccessProvider.class);
			Class<? extends BeanAccessProvider<?>> type = metaAnnotation.value();
			return beanInjector.createInstance(type);
		} catch (Exception exception) {
			throw new BeanAccessProviderException(
					String.format("Could not create BeanAccessProvider for annotation %s", annotation.getName()), exception);
		}
	}

}
