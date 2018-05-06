package com.happyfoods.utilities.reflection;

import javax.annotation.Nullable;
import java.lang.reflect.*;
import java.util.List;
import java.util.logging.Logger;

public class ReflectionUtilities {

	private static final Logger logger = Logger.getLogger(ReflectionUtilities.class.getSimpleName());

	public static @Nullable <T> T createInstance(Class<T> classToInstance) {
		try {
			return classToInstance.newInstance();
		} catch (Exception e) {
			logger.severe(e.getMessage());
			return null;
		}
	}

	public static @Nullable <T> T createInstance(Class<T> classToInstance, List<Class<?>> argumentTypes, Object... arguments) {
		try {
			Constructor<T> constructor = classToInstance.getDeclaredConstructor(argumentTypes.toArray(new Class<?>[0]));
			return constructor.newInstance(arguments);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			return null;
		}
	}

	public static @Nullable Method getMethod(Class<?> classWithMethod, String methodName, Class<?>... parameterTypes) {
		try {
			return classWithMethod.getDeclaredMethod(methodName, parameterTypes);
		} catch (NoSuchMethodException e) {
			logger.warning("Could not find " + classWithMethod + "." + methodName + "()");
			return null;
		}
	}

	public static @Nullable <T> T invokePublicMethod(Object instanceToInvokeOn, Method method, Class<T> returnType, Object... arguments) {
		try {
			Object returnValue = method.invoke(instanceToInvokeOn, arguments);
			return returnType.cast(returnValue);
		} catch (IllegalAccessException | InvocationTargetException | ClassCastException e) {
			logger.warning(e.getMessage());
			return null;
		}
	}

	public static void invokePublicMethod(Object instanceToInvokeOn, Method method, Object... arguments) {
		try {
			method.invoke(instanceToInvokeOn, arguments);
		} catch (IllegalAccessException | InvocationTargetException | ClassCastException e) {
			logger.warning(e.getMessage());
		}
	}

	public static @Nullable Field getField(Class<?> classWithField, String fieldName) {
		try {
			return classWithField.getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
			logger.warning("Could not find " + classWithField + "." + fieldName);
			return null;
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