package com.happyfoods.utilities.reflection;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class ReflectionUtilitiesTest {
	private static final String DEFAULT_PRIVATE_VALUE = "defaultPrivateValue";
	private static final String DEFAULT_PRIVATE_STATIC_VALUE = "defaultPrivateStaticValue";
	private static final Logger DEFAULT_PRIVATE_STATIC_FINAL_VALUE = getLogger(ClassWithPrivateStuff.class.getSimpleName());
	private static final String DEFAULT_CONSTANT_VALUE = "defaultConstantValue";
	private static final String DEFAULT_PUBLIC_VALUE = "defaultPublicValue";
	private static final String DEFAULT_PUBLIC_STATIC_VALUE = "defaultPublicStaticValue";
	private static final String ERROR_CANNOT_ACCESS_PRIVATE_MEMBER = "Class com.happyfoods.utilities.reflection.ReflectionUtilities can not access a member of class com.happyfoods.utilities.reflection.ReflectionUtilitiesTest$ClassWithPrivateStuff with modifiers \"private\"";
	private static final String COULD_NOT_FIND_METHOD = "Could not find class com.happyfoods.utilities.reflection.ReflectionUtilitiesTest$ClassWithPrivateStuff.getNonExistingMethod()";

	@Spy
	private Logger logger = getLogger(ReflectionUtilitiesTest.class.getSimpleName());

	@Before
	public void beforeEachTest() {
		// This is so meta.
		Field loggerField = ReflectionUtilities.getField(ReflectionUtilities.class, "logger");
		ReflectionUtilities.setPrivateStaticFinalField(loggerField, logger);
	}

	@Test
	public void testThatFailingToCreateAnInstanceOfPrivateClassLogsAndReturnsNull() {
		assertThat(ReflectionUtilities.createInstance(ClassWithPrivateStuff.class)).isNull();
		verify(logger).severe(ERROR_CANNOT_ACCESS_PRIVATE_MEMBER);
	}

	@Test
	public void testCreatingAnInstanceOfPublicClass() {
		assertThat(ReflectionUtilities.createInstance(ClassWithPublicStuff.class)).isNotNull();
	}

	@Test
	public void testThatCreatingAnInstanceOfPrivateClassLogsAndReturnsNull() {
		ClassWithPrivateStuff instance = ReflectionUtilities.createInstance(ClassWithPrivateStuff.class,
				ImmutableList.of(String.class, Integer.class),
				"argumentOne", 2);
		assertThat(instance).isNull();
		verify(logger).severe(ERROR_CANNOT_ACCESS_PRIVATE_MEMBER);
	}

	@Test
	public void testCreatingAnInstanceOfPublicClassUsingMultipleArguments() {
		ClassWithPublicStuff instance = ReflectionUtilities.createInstance(ClassWithPublicStuff.class,
				ImmutableList.of(String.class, Integer.class),
				"argumentOne", 2);
		assertThat(instance).isNotNull();
	}

	@Test
	public void testThatNonExistingMethodThrowsLogsAndReturnsNull() {
		assertThat(ReflectionUtilities.getMethod(ClassWithPrivateStuff.class, "getNonExistingMethod")).isNull();
		verify(logger).warning(COULD_NOT_FIND_METHOD);
	}

	@Test
	public void testThatPrivateMethodCanBeRetrievedFromClass() {
		assertThat(ReflectionUtilities.getMethod(ClassWithPrivateStuff.class, "getPrivateValue")).isNotNull();
	}

	@Test
	public void testThatPublicMethodCanBeRetrievedFromClass() {
		assertThat(ReflectionUtilities.getMethod(ClassWithPublicStuff.class, "getPublicValue")).isNotNull();
	}

	@Test
	public void testThatInvokingAccessorMethodWithWrongClassTypeLogsAndReturnsNull() {
		ClassWithPublicStuff instance = new ClassWithPublicStuff();
		Method method = ReflectionUtilities.getMethod(ClassWithPublicStuff.class, "getPublicValue");
		WrongClassType accessorValueOfWrongType = ReflectionUtilities.invokePublicMethod(instance, method, WrongClassType.class);
		assertThat(accessorValueOfWrongType).isNull();
		verify(logger).warning("Cannot cast java.lang.String to com.happyfoods.utilities.reflection.ReflectionUtilitiesTest$WrongClassType");
	}

	@Test
	public void testThatPrivateAccessorMethodCannotBeInvoked() {
		ClassWithPrivateStuff instance = new ClassWithPrivateStuff();
		Method method = ReflectionUtilities.getMethod(ClassWithPrivateStuff.class, "getPrivateValue");
		String accessorValue = ReflectionUtilities.invokePublicMethod(instance, method, String.class);
		assertThat(accessorValue).isNull();
		verify(logger).warning(ERROR_CANNOT_ACCESS_PRIVATE_MEMBER);
	}

	@Test
	public void testThatPublicAccessorMethodCanBeInvoked() {
		ClassWithPublicStuff instance = new ClassWithPublicStuff();
		Method method = ReflectionUtilities.getMethod(ClassWithPublicStuff.class, "getPublicValue");
		String accessorValue = ReflectionUtilities.invokePublicMethod(instance, method, String.class);
		assertThat(accessorValue).isEqualTo(DEFAULT_PUBLIC_VALUE);
	}

	@Test
	public void testThatPrivateMutatorMethodCannotBeCalled() {
		ClassWithPrivateStuff instance = new ClassWithPrivateStuff();
		Method method = ReflectionUtilities.getMethod(ClassWithPrivateStuff.class, "setPrivateValue", String.class);
		ReflectionUtilities.invokePublicMethod(instance, method, "argument");
		assertThat(instance.getPrivateValue()).isEqualTo(DEFAULT_PRIVATE_VALUE);
	}

	@Test
	public void testThatPublicMutatorMethodCanBeCalled() {
		ClassWithPublicStuff instance = new ClassWithPublicStuff();
		Method method = ReflectionUtilities.getMethod(ClassWithPublicStuff.class, "setPublicValue", String.class);
		ReflectionUtilities.invokePublicMethod(instance, method, "argument");
		assertThat(instance.getPublicValue()).isEqualTo("argument");
	}

	@Test
	public void testThatWrongFieldNameLogsAndReturnsNullWhenRetrievingField() {
		assertThat(ReflectionUtilities.getField(ClassWithPublicStuff.class, "nonExistingFieldName")).isNull();
		verify(logger).warning("Could not find class com.happyfoods.utilities.reflection.ReflectionUtilitiesTest$ClassWithPublicStuff.nonExistingFieldName");
	}

	@Test
	public void testThatPrivateFieldCanBeRetrievedFromClass() {
		assertThat(ReflectionUtilities.getField(ClassWithPrivateStuff.class, "privateValue")).isNotNull();
	}

	@Test
	public void testThatPublicFieldCanBeRetrievedFromClass() {
		assertThat(ReflectionUtilities.getField(ClassWithPublicStuff.class, "publicValue")).isNotNull();
	}

	@Test
	public void testThatChangingFieldOfAnotherClassLogs() {
		ClassWithPublicStuff instance = new ClassWithPublicStuff();
		Field publicFieldOfAnotherClass = ReflectionUtilities.getField(WrongClassType.class, "publicValue");
		ReflectionUtilities.setPublicField(publicFieldOfAnotherClass, instance, "argument");
		assertThat(instance.getPublicValue()).isEqualTo(DEFAULT_PUBLIC_VALUE);
		verify(logger).warning("Can not set java.lang.String field com.happyfoods.utilities.reflection.ReflectionUtilitiesTest$WrongClassType.publicValue to com.happyfoods.utilities.reflection.ReflectionUtilitiesTest$ClassWithPublicStuff");
	}

	@Test
	public void testThatPublicFieldInInstanceCanBeChanged() {
		ClassWithPublicStuff instance = new ClassWithPublicStuff();
		Field publicField = ReflectionUtilities.getField(ClassWithPublicStuff.class, "publicValue");
		ReflectionUtilities.setPublicField(publicField, instance, "argument");
		assertThat(instance.getPublicValue()).isEqualTo("argument");
	}

	@Test
	public void testThatPublicStaticFieldInClassCanBeChanged() {
		Field publicStaticField = ReflectionUtilities.getField(ClassWithPublicStuff.class, "publicStaticValue");
		ReflectionUtilities.setPublicField(publicStaticField, "argument");
		assertThat(ClassWithPublicStuff.getPublicStaticValue()).isEqualTo("argument");
	}

	@Test
	public void testThatPrivateFieldOfInstanceCanBeChanged() {
		ClassWithPrivateStuff instance = new ClassWithPrivateStuff();
		Field privateField = ReflectionUtilities.getField(ClassWithPrivateStuff.class, "privateValue");
		ReflectionUtilities.setPrivateField(privateField, instance, "argument");
		assertThat(instance.getPrivateValue()).isEqualTo("argument");
	}

	@Test
	public void testThatPrivateStaticFieldOfClassCanBeChanged() {
		Field privateField = ReflectionUtilities.getField(ClassWithPrivateStuff.class, "privateStaticValue");
		ReflectionUtilities.setPrivateField(privateField, "argument");
		assertThat(ClassWithPrivateStuff.getPrivateStaticValue()).isEqualTo("argument");
	}

	@Test
	public void testThatConstantCannotBeChanged() {
		Field privateStaticFinalField = ReflectionUtilities.getField(ClassWithPrivateStuff.class, "CONSTANT_VALUE");
		ReflectionUtilities.setPrivateStaticFinalField(privateStaticFinalField, "NEW VALUE");
		assertThat(ClassWithPrivateStuff.getConstantValue()).isEqualTo(DEFAULT_CONSTANT_VALUE);
		verifyZeroInteractions(logger);
	}

	@Test
	public void testThatPrivateStaticFinalFieldCanBeChanged() {
		Field privateStaticFinalField = ReflectionUtilities.getField(ClassWithPrivateStuff.class, "privateStaticFinalValue");
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

		public static String getPrivateStaticValue() {
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
		public String publicValue = DEFAULT_PUBLIC_VALUE;
		public static String publicStaticValue = DEFAULT_PUBLIC_STATIC_VALUE;

		public ClassWithPublicStuff() {
		}

		public ClassWithPublicStuff(String parameterOne, Integer parameterTwo) {

		}

		public static String getPublicStaticValue() {
			return publicStaticValue;
		}

		public String getPublicValue() {
			return publicValue;
		}

		public void setPublicValue(String parameter) {
			publicValue = parameter;
		}
	}

	protected static class WrongClassType {
		public String publicValue = DEFAULT_PUBLIC_VALUE;
	}
}