package com.happyfoods.utilities.reflection;

import com.google.common.collect.ImmutableList;
import com.happyfoods.framework.TestExtension;
import com.happyfoods.utilities.annotation.TagFast;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@TagFast
@ExtendWith(TestExtension.class)
class ReflectionUtilitiesTest {
	private static final String DEFAULT_PRIVATE_VALUE = "defaultPrivateValue";
	private static final String DEFAULT_PRIVATE_STATIC_VALUE = "defaultPrivateStaticValue";
	private static final Logger DEFAULT_PRIVATE_STATIC_FINAL_VALUE = getLogger(ClassWithPrivateStuff.class.getSimpleName());
	private static final String DEFAULT_CONSTANT_VALUE = "defaultConstantValue";
	private static final String DEFAULT_PUBLIC_VALUE = "defaultPublicValue";
	private static final String DEFAULT_PUBLIC_STATIC_VALUE = "defaultPublicStaticValue";
	private static final String ERROR_CANNOT_ACCESS_PRIVATE_MEMBER = String.format("class %s cannot access a member of class %s with modifiers \"private\"",
			ReflectionUtilities.class.getName(), ClassWithPrivateStuff.class.getName());

	@Spy
	private Logger logger = getLogger(ReflectionUtilitiesTest.class.getSimpleName());

	@BeforeEach
	void beforeEachTest() {
		// This is so meta.
		Field loggerField = ReflectionUtilities.getField(ReflectionUtilities.class, "logger").get();
		ReflectionUtilities.setPrivateStaticFinalField(loggerField, logger);
	}

	@Test
	void testThatFailingToCreateAnInstanceOfPrivateClassLogsAndReturnsNull() {
		assertThat(ReflectionUtilities.createInstance(ClassWithPrivateStuff.class)).isEmpty();
		verify(logger).severe(ERROR_CANNOT_ACCESS_PRIVATE_MEMBER);
	}

	@Test
	void testCreatingAnInstanceOfPublicClass() {
		assertThat(ReflectionUtilities.createInstance(ClassWithPublicStuff.class)).isNotEmpty();
	}

	@Test
	void testCreatingAnInstanceOfPublicClassWithPrivateConstructor() {
		assertThat(ReflectionUtilities.createInstanceUsingPrivateConstructor(ClassWithPrivateStuff.class)).isNotEmpty();
	}

	@Test
	void testThatCreatingAnInstanceOfPrivateClassLogsAndReturnsNull() {
		Optional<ClassWithPrivateStuff> instance = ReflectionUtilities.createInstance(ClassWithPrivateStuff.class,
				ImmutableList.of(String.class, Integer.class),
				"argumentOne", 2);
		assertThat(instance).isEmpty();
		verify(logger).severe(ERROR_CANNOT_ACCESS_PRIVATE_MEMBER);
	}

	@Test
	void testCreatingAnInstanceOfPublicClassUsingMultipleArguments() {
		Optional<ClassWithPublicStuff> instance = ReflectionUtilities.createInstance(ClassWithPublicStuff.class,
				ImmutableList.of(String.class, Integer.class),
				"argumentOne", 2);
		assertThat(instance).isNotEmpty();
	}

	@Test
	void testThatNonExistingMethodThrowsLogsAndReturnsNull() {
		assertThat(ReflectionUtilities.getMethod(ClassWithPrivateStuff.class, "getNonExistingMethod")).isEmpty();
		verify(logger).warning(String.format("Could not find class %s.getNonExistingMethod()", ClassWithPrivateStuff.class.getName()));
	}

	@Test
	void testThatPrivateMethodCanBeRetrievedFromClass() {
		assertThat(ReflectionUtilities.getMethod(ClassWithPrivateStuff.class, "getPrivateValue")).isNotEmpty();
	}

	@Test
	void testThatPublicMethodCanBeRetrievedFromClass() {
		assertThat(ReflectionUtilities.getMethod(ClassWithPublicStuff.class, "getPublicValue")).isNotEmpty();
	}

	@Test
	void testThatInvokingAccessorMethodWithWrongClassTypeLogsAndReturnsNull() {
		ClassWithPublicStuff instance = new ClassWithPublicStuff();
		Method method = ReflectionUtilities.getMethod(ClassWithPublicStuff.class, "getPublicValue").get();
		Optional<WrongClassType> accessorValueOfWrongType = ReflectionUtilities.invokePublicMethod(instance, method, WrongClassType.class);
		assertThat(accessorValueOfWrongType).isEmpty();
		verify(logger).warning(String.format("Cannot cast java.lang.String to %s", WrongClassType.class.getName()));
	}

	@Test
	void testThatPrivateAccessorMethodCannotBeInvoked() {
		ClassWithPrivateStuff instance = new ClassWithPrivateStuff();
		Method method = ReflectionUtilities.getMethod(ClassWithPrivateStuff.class, "getPrivateValue").get();
		Optional<String> accessorValue = ReflectionUtilities.invokePublicMethod(instance, method, String.class);
		assertThat(accessorValue).isEmpty();
		verify(logger).warning(ERROR_CANNOT_ACCESS_PRIVATE_MEMBER);
	}

	@Test
	void testThatPublicAccessorMethodCanBeInvoked() {
		ClassWithPublicStuff instance = new ClassWithPublicStuff();
		Method method = ReflectionUtilities.getMethod(ClassWithPublicStuff.class, "getPublicValue").get();
		Optional<String> accessorValue = ReflectionUtilities.invokePublicMethod(instance, method, String.class);
		assertThat(accessorValue).contains(DEFAULT_PUBLIC_VALUE);
	}

	@Test
	void testThatPrivateMutatorMethodCannotBeCalled() {
		ClassWithPrivateStuff instance = new ClassWithPrivateStuff();
		Method method = ReflectionUtilities.getMethod(ClassWithPrivateStuff.class, "setPrivateValue", String.class).get();
		ReflectionUtilities.invokePublicMethod(instance, method, "argument");
		assertThat(instance.getPrivateValue()).isEqualTo(DEFAULT_PRIVATE_VALUE);
	}

	@Test
	void testThatPublicMutatorMethodCanBeCalled() {
		ClassWithPublicStuff instance = new ClassWithPublicStuff();
		Method method = ReflectionUtilities.getMethod(ClassWithPublicStuff.class, "setPublicValue", String.class).get();
		ReflectionUtilities.invokePublicMethod(instance, method, "argument");
		assertThat(instance.getPublicValue()).isEqualTo("argument");
	}

	@Test
	void testThatWrongFieldNameLogsAndReturnsNullWhenRetrievingField() {
		assertThat(ReflectionUtilities.getField(ClassWithPublicStuff.class, "nonExistingFieldName")).isEmpty();
		verify(logger).warning(String.format("Could not find class %s.nonExistingFieldName", ClassWithPublicStuff.class.getName()));
	}

	@Test
	void testThatPrivateFieldCanBeRetrievedFromClass() {
		assertThat(ReflectionUtilities.getField(ClassWithPrivateStuff.class, "privateValue")).isNotEmpty();
	}

	@Test
	void testThatPublicFieldCanBeRetrievedFromClass() {
		assertThat(ReflectionUtilities.getField(ClassWithPublicStuff.class, "publicValue")).isNotEmpty();
	}

	@Test
	void testThatChangingFieldOfAnotherClassLogs() {
		ClassWithPublicStuff instance = new ClassWithPublicStuff();
		Field publicFieldOfAnotherClass = ReflectionUtilities.getField(WrongClassType.class, "publicValue").get();
		ReflectionUtilities.setPublicField(publicFieldOfAnotherClass, instance, "argument");
		assertThat(instance.getPublicValue()).isEqualTo(DEFAULT_PUBLIC_VALUE);
		verify(logger).warning(String.format("Can not set java.lang.String field %s.publicValue to %s",
				WrongClassType.class.getName(), ClassWithPublicStuff.class.getName()));
	}

	@Test
	void testThatPublicFieldInInstanceCanBeChanged() {
		ClassWithPublicStuff instance = new ClassWithPublicStuff();
		Field publicField = ReflectionUtilities.getField(ClassWithPublicStuff.class, "publicValue").get();
		ReflectionUtilities.setPublicField(publicField, instance, "argument");
		assertThat(instance.getPublicValue()).isEqualTo("argument");
	}

	@Test
	void testThatPublicStaticFieldInClassCanBeChanged() {
		Field publicStaticField = ReflectionUtilities.getField(ClassWithPublicStuff.class, "publicStaticValue").get();
		ReflectionUtilities.setPublicField(publicStaticField, "argument");
		assertThat(ClassWithPublicStuff.getPublicStaticValue()).isEqualTo("argument");
	}

	@Test
	void testThatPrivateFieldOfInstanceCanBeChanged() {
		ClassWithPrivateStuff instance = new ClassWithPrivateStuff();
		Field privateField = ReflectionUtilities.getField(ClassWithPrivateStuff.class, "privateValue").get();
		ReflectionUtilities.setPrivateField(privateField, instance, "argument");
		assertThat(instance.getPrivateValue()).isEqualTo("argument");
	}

	@Test
	void testThatPrivateStaticFieldOfClassCanBeChanged() {
		Field privateField = ReflectionUtilities.getField(ClassWithPrivateStuff.class, "privateStaticValue").get();
		ReflectionUtilities.setPrivateField(privateField, "argument");
		assertThat(ClassWithPrivateStuff.getPrivateStaticValue()).isEqualTo("argument");
	}

	@Test
	void testThatConstantCannotBeChanged() {
		Field privateStaticFinalField = ReflectionUtilities.getField(ClassWithPrivateStuff.class, "CONSTANT_VALUE").get();
		ReflectionUtilities.setPrivateStaticFinalField(privateStaticFinalField, "NEW VALUE");
		assertThat(ClassWithPrivateStuff.getConstantValue()).isEqualTo(DEFAULT_CONSTANT_VALUE);
		verifyZeroInteractions(logger);
	}

	@Test
	void testThatPrivateStaticFinalFieldCanBeChanged() {
		Field privateStaticFinalField = ReflectionUtilities.getField(ClassWithPrivateStuff.class, "privateStaticFinalValue").get();
		Logger newValue = getLogger(ReflectionUtilitiesTest.class.getSimpleName());
		ReflectionUtilities.setPrivateStaticFinalField(privateStaticFinalField, newValue);
		assertThat(ClassWithPrivateStuff.getPrivateStaticFinalValue()).isEqualTo(newValue);
	}

	protected static class ClassWithPrivateStuff {
		private String privateValue = DEFAULT_PRIVATE_VALUE;
		private static String privateStaticValue = DEFAULT_PRIVATE_STATIC_VALUE;
		private static final String CONSTANT_VALUE = DEFAULT_CONSTANT_VALUE;
		private static final Logger privateStaticFinalValue = DEFAULT_PRIVATE_STATIC_FINAL_VALUE;

		private ClassWithPrivateStuff() {
		}

		private ClassWithPrivateStuff(String parameterOne, Integer parameterTwo) {

		}

		static String getPrivateStaticValue() {
			return privateStaticValue;
		}

		private String getPrivateValue() {
			return privateValue;
		}

		private static String getConstantValue() {
			return CONSTANT_VALUE;
		}

		private static Logger getPrivateStaticFinalValue() {
			return privateStaticFinalValue;
		}

		private void setPrivateValue(String parameter) {
			privateValue = parameter;
		}
	}

	protected static class ClassWithPublicStuff {
		String publicValue = DEFAULT_PUBLIC_VALUE;
		static String publicStaticValue = DEFAULT_PUBLIC_STATIC_VALUE;

		ClassWithPublicStuff() {
		}

		ClassWithPublicStuff(String parameterOne, Integer parameterTwo) {

		}

		static String getPublicStaticValue() {
			return publicStaticValue;
		}

		String getPublicValue() {
			return publicValue;
		}

		void setPublicValue(String parameter) {
			publicValue = parameter;
		}
	}

	protected static class WrongClassType {
		String publicValue = DEFAULT_PUBLIC_VALUE;
	}
}
