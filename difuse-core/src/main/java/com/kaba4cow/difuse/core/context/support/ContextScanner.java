package com.kaba4cow.difuse.core.context.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.context.Context;
import com.kaba4cow.difuse.core.util.reflections.PackageScanner;

@SystemBean
public class ContextScanner {

	private final Map<Class<?>, PackageScanner> pool = new ConcurrentHashMap<>();

	public PackageScanner getScanner(Class<?> sourceClass) {
		return pool.computeIfAbsent(sourceClass, PackageScanner::of);
	}

	public PackageScanner getScanner(Context context) {
		return getScanner(context.getSourceClass());
	}

}
