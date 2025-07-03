package com.kaba4cow.difuse.core.test;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.BiFunction;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

import com.kaba4cow.difuse.core.DifuseApplication;
import com.kaba4cow.difuse.core.annotation.dependency.Property;
import com.kaba4cow.difuse.core.annotation.dependency.Provided;
import com.kaba4cow.difuse.core.dependency.DependencyConsumer;
import com.kaba4cow.difuse.core.dependency.provider.DependencyProvider;
import com.kaba4cow.difuse.core.test.annotation.AssertThrows;
import com.kaba4cow.difuse.core.test.annotation.SeparateThread;
import com.kaba4cow.difuse.core.test.annotation.TestContext;

public class DifuseTestExtension implements //
		BeforeEachCallback, //
		BeforeAllCallback, //
		ParameterResolver, //
		InvocationInterceptor, //
		TestExecutionExceptionHandler {

	private static final ExecutorService executor = Executors.newSingleThreadExecutor();

	private static BiFunction<AnnotatedElement, Type, Object> dependencySupplier;

	@Override
	public void beforeAll(ExtensionContext context) throws Exception {
		Class<?> testClass = context.getRequiredTestClass();
		Class<?> sourceClass = testClass.getAnnotation(TestContext.class).value();
		DependencyConsumer dependencyConsumer = new TestDependencyConsumer(testClass);
		DependencyProvider dependencyProvider = new DifuseApplication(sourceClass).asTest(testClass).run();
		dependencySupplier = (field, type) -> dependencyProvider.provideDependency(field, type, dependencyConsumer);
	}

	@Override
	public void beforeEach(ExtensionContext extensionContext) throws Exception {
		Object testInstance = extensionContext.getRequiredTestInstance();
		for (Field field : testInstance.getClass().getDeclaredFields())
			setTestInstanceField(field, testInstance);
	}

	private void setTestInstanceField(Field field, Object testInstance) throws Exception {
		if (field.isAnnotationPresent(Provided.class) || field.isAnnotationPresent(Property.class)) {
			field.setAccessible(true);
			Object dependency;
			if (!field.isAnnotationPresent(SeparateThread.class))
				dependency = dependencySupplier.apply(field, field.getGenericType());
			else {
				Future<?> future = executor.submit(() -> dependencySupplier.apply(field, field.getGenericType()));
				dependency = future.get();
			}
			if (Modifier.isStatic(field.getModifiers()))
				field.set(null, dependency);
			else
				field.set(testInstance, dependency);
		}
	}

	@Override
	public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		return true;
	}

	@Override
	public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		AnnotatedElement element = parameterContext.getAnnotatedElement();
		Type type = parameterContext.getParameter().getParameterizedType();
		return dependencySupplier.apply(element, type);
	}

	@Override
	public void interceptTestMethod(Invocation<Void> invocation, ReflectiveInvocationContext<Method> invocationContext,
			ExtensionContext extensionContext) throws Throwable {
		Method method = invocationContext.getExecutable();
		if (!method.isAnnotationPresent(SeparateThread.class))
			invocation.proceed();
		else {
			if (method.getParameterCount() != 0)
				throw new RuntimeException("@SeparateThread method must accept no parameters");
			Future<?> future = executor.submit(() -> {
				try {
					invocation.proceed();
				} catch (Throwable throwable) {
					throw new CompletionException(throwable);
				}
			});
			try {
				future.get();
			} catch (ExecutionException exception) {
				throw exception.getCause();
			}
		}
	}

	@Override
	public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
		Method method = context.getRequiredTestMethod();
		if (!method.isAnnotationPresent(AssertThrows.class))
			throw throwable;
		AssertThrows annotation = method.getAnnotation(AssertThrows.class);

		Class<? extends Throwable> expectedThrowable = annotation.value();
		Throwable actualThrowable = getActualThrowable(throwable);
		if (!expectedThrowable.isAssignableFrom(actualThrowable.getClass()))
			throw new AssertionError(String.format("Expected throwable of type %s but got %s", expectedThrowable.getName(),
					actualThrowable.getClass().getName()), actualThrowable);

		String expectedMessage = annotation.withMessage();
		String actualMessage = actualThrowable.getMessage();
		if (!expectedMessage.isEmpty())
			if (Objects.isNull(actualMessage))
				throw new AssertionError(
						String.format("Expected exception message to contain '%s' but was null", expectedMessage),
						actualThrowable);
			else if (!actualMessage.contains(expectedMessage))
				throw new AssertionError(String.format("Expected exception message to contain '%s' but was '%s'",
						expectedMessage, actualMessage), actualThrowable);
	}

	private Throwable getActualThrowable(Throwable thrown) {
		return thrown instanceof ParameterResolutionException//
				? thrown.getCause()//
				: thrown;
	}

}
