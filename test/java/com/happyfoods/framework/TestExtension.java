package com.happyfoods.framework;

import com.happyfoods.utilities.reflection.ReflectionUtilities;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.quality.Strictness;

import static java.util.Collections.singletonList;

public class TestExtension implements TestInstancePostProcessor, BeforeEachCallback, AfterEachCallback {

	private MockitoExtension mockitoExtension;

	// This constructor is invoked by JUnit Jupiter via reflection
	private TestExtension() {
		this(Strictness.STRICT_STUBS);
	}

	private TestExtension(Strictness strictness) {
		mockitoExtension = ReflectionUtilities.createInstanceUsingPrivateConstructor(MockitoExtension.class, singletonList(Strictness.class), strictness).orElseThrow();
		System.err.close();
	}

	@Override
	public void postProcessTestInstance(Object testInstance, ExtensionContext context) {
		mockitoExtension.postProcessTestInstance(testInstance, context);
	}

	@Override
	public void beforeEach(ExtensionContext context) {
		mockitoExtension.beforeEach(context);
	}

	@Override
	public void afterEach(ExtensionContext context) {
		mockitoExtension.afterEach(context);
	}
}
