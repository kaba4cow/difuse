package com.kaba4cow.difuse.core.application;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.kaba4cow.difuse.core.annotation.application.CoreComponent;
import com.kaba4cow.difuse.core.util.reflections.PackageScanner;

@CoreComponent
public class PackageScannerPool {

	private final Map<Class<?>, PackageScanner> pool = new ConcurrentHashMap<>();

	public PackageScanner getPackageScanner(Class<?> sourceClass) {
		return pool.computeIfAbsent(sourceClass, PackageScanner::of);
	}

}
