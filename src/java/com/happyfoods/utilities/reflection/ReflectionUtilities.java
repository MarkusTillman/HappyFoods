package com.happyfoods.utilities.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class ReflectionUtilities {

	private static final Logger logger = Logger.getLogger(ReflectionUtilities.class.getSimpleName());

	public static <T> Optional<T> createInstance(Class<T> classToInstance) {
		return createInstance(classToInstance, false);
	}

	public static <T> Optional<T> createInstanceUsingPrivateConstructor(Class<T> classToInstance) {
		return createInstance(classToInstance, true);
	}

	private static <T> Optional<T> createInstance(Class<T> classToInstance, boolean usingPrivateConstructor) {
		try {
			Constructor<T> constructor = classToInstance.getDeclaredConstructor();
			constructor.setAccessible(usingPrivateConstructor);
			return Optional.of(constructor.newInstance());
		} catch (Exception e) {
			logger.severe(e.getMessage());
			return Optional.empty();
		}
	}

	public static <T> Optional<T> createInstance(Class<T> classToInstance, List<Class<?>> argumentTypes, Object... arguments) {
		return createInstance(classToInstance, false, argumentTypes, arguments);
	}

	public static <T> Optional<T> createInstanceUsingPrivateConstructor(Class<T> classToInstance, List<Class<?>> argumentTypes, Object... arguments) {
		return createInstance(classToInstance, true, argumentTypes, arguments);
	}

	private static <T> Optional<T> createInstance(Class<T> classToInstance, boolean usingPrivateConstructor, List<Class<?>> argumentTypes, Object[] arguments) {
		try {
			Constructor<T> constructor = classToInstance.getDeclaredConstructor(argumentTypes.toArray(new Class<?>[0]));
			constructor.setAccessible(usingPrivateConstructor);
			return Optional.of(constructor.newInstance(arguments));
		} catch (Exception e) {
			logger.severe(e.getMessage());
			return Optional.empty();
		}
	}

	public static Optional<Method> getMethod(Class<?> classWithMethod, String methodName, Class<?>... parameterTypes) {
		try {
			return Optional.ofNullable(classWithMethod.getDeclaredMethod(methodName, parameterTypes));
		} catch (NoSuchMethodException e) {
			logger.warning("Could not find " + classWithMethod + "." + methodName + "()");
			return Optional.empty();
		}
	}

	public static <T> Optional<T> invokePublicMethod(Object instanceToInvokeOn, Method method, Class<T> returnType, Object... arguments) {
		try {
			Object returnValue = method.invoke(instanceToInvokeOn, arguments);
			return Optional.ofNullable(returnType.cast(returnValue));
		} catch (IllegalAccessException | InvocationTargetException | ClassCastException e) {
			logger.warning(e.getMessage());
			return Optional.empty();
		}
	}

	public static void invokePublicMethod(Object instanceToInvokeOn, Method method, Object... arguments) {
		try {
			method.invoke(instanceToInvokeOn, arguments);
		} catch (IllegalAccessException | InvocationTargetException | ClassCastException e) {
			logger.warning(e.getMessage());
		}
	}

	public static Optional<Field> getField(Class<?> classWithField, String fieldName) {
		try {
			return Optional.ofNullable(classWithField.getDeclaredField(fieldName));
		} catch (NoSuchFieldException e) {
			logger.warning("Could not find " + classWithField + "." + fieldName);
			return Optional.empty();
		}
	}

	public static void setPublicField(Field field, Object instanceToUse, Object value) {
		try {
			field.set(instanceToUse, value);
		} catch (IllegalAccessException | IllegalArgumentException e) {
			logger.warning(e.getMessage());
		}
	}

	public static void setPublicField(Field field, Object value) {
		setPublicField(field, null, value);
	}

	public static void setPrivateField(Field field, Object instanceToUse, Object value) {
		field.setAccessible(true);
		setPublicField(field, instanceToUse, value);
	}

	public static void setPrivateField(Field field, Object value) {
		field.setAccessible(true);
		setPublicField(field, value);
	}

	public static void setPrivateStaticFinalField(Field field, Object value) {
		field.setAccessible(true);
		removeModifiersFrom(field, value);
		setPublicField(field, null, value);
	}

	private static void removeModifiersFrom(Field field, Object value) {
		try {
			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
			field.set(null, value);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			logger.warning(e.getMessage());
		}
	}
}
