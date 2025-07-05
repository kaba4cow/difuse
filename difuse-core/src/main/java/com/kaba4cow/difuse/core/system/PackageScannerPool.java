package com.kaba4cow.difuse.core.system;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.util.reflections.PackageScanner;

@SystemBean
public class PackageScannerPool {

	private final Map<Class<?>, PackageScanner> pool = new ConcurrentHashMap<>();

	public PackageScanner getPackageScanner(Class<?> sourceClass) {
		return pool.computeIfAbsent(sourceClass, PackageScanner::of);
	}

}
