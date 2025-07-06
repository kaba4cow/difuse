package com.kaba4cow.difuse.core.bean.access.provider.support;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

import com.kaba4cow.difuse.core.annotation.access.AccessProvider;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.bean.access.provider.BeanAccessProvider;
import com.kaba4cow.difuse.core.bean.access.provider.BeanAccessProviderException;
import com.kaba4cow.difuse.core.system.bean.SystemBeanInjector;
import com.kaba4cow.difuse.core.util.reflections.ConstructorScanner;

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
			Constructor<?> constructor = ConstructorScanner.of(type).findNoArgsConstructor();
			Object instance = constructor.newInstance();
			beanInjector.injectDependencies(instance);
			return type.cast(instance);
		} catch (Exception exception) {
			throw new BeanAccessProviderException(
					String.format("Could not create BeanAccessProvider for annotation %s", annotation.getName()), exception);
		}
	}

}
