package com.kaba4cow.difuse.core.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.function.Supplier;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.LazyLoader;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class ProxyFactory {

	private ProxyFactory() {}

	public static <T> T createProxy(Class<T> type, InvocationHandler invocationHandler) {
		return type.isInterface()//
				? createInterfaceProxy(type, invocationHandler)//
				: createClassProxy(type, invocationHandler);
	}

	public static <T> T createLazyProxy(Class<T> type, Supplier<?> instanceSupplier) {
		return type.isInterface()//
				? createLazyInterfaceProxy(type, instanceSupplier)//
				: createLazyClassProxy(type, instanceSupplier);
	}

	private static <T> T createInterfaceProxy(Class<T> type, InvocationHandler invocationHandler) {
		return type.cast(Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[] { type }, invocationHandler));
	}

	private static <T> T createLazyInterfaceProxy(Class<T> type, Supplier<?> instanceSupplier) {
		return createInterfaceProxy(type, new LazyInterfaceLoader(instanceSupplier));
	}

	private static <T> T createClassProxy(Class<T> type, Callback callback) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(type);
		enhancer.setCallback(callback);
		return type.cast(enhancer.create());
	}

	private static <T> T createClassProxy(Class<T> type, InvocationHandler invocationHandler) {
		return createClassProxy(type, new MethodCallback(invocationHandler));
	}

	private static <T> T createLazyClassProxy(Class<T> type, Supplier<?> instanceSupplier) {
		return createClassProxy(type, new LazyClassLoader(instanceSupplier));
	}

	private static class LazyInterfaceLoader implements InvocationHandler {

		private final Supplier<?> instanceSupplier;

		private volatile Object instance;

		private LazyInterfaceLoader(Supplier<?> instanceSupplier) {
			this.instanceSupplier = instanceSupplier;
			this.instance = null;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			if (instance == null)
				synchronized (this) {
					if (instance == null)
						instance = instanceSupplier.get();
				}
			return method.invoke(instance, args);
		}

	}

	private static class LazyClassLoader implements LazyLoader {

		private final Supplier<?> instanceSupplier;

		private LazyClassLoader(Supplier<?> instanceSupplier) {
			this.instanceSupplier = instanceSupplier;
		}

		@Override
		public Object loadObject() throws Exception {
			return instanceSupplier.get();
		}

	}

	private static class MethodCallback implements MethodInterceptor {

		private final InvocationHandler invocationHandler;

		private MethodCallback(InvocationHandler invocationHandler) {
			this.invocationHandler = invocationHandler;
		}

		@Override
		public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
			return invocationHandler.invoke(obj, method, args);
		}

	}

}
