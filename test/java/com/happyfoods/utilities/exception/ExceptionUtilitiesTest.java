package com.happyfoods.utilities.exception;

import com.happyfoods.utilities.reflection.ReflectionUtilities;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class ExceptionUtilitiesTest {

	@Mock
	private Logger logger;

	@Before
	public void beforeEachTest() {
		Field loggerField = ReflectionUtilities.getField(ExceptionUtilities.class, "logger");
		ReflectionUtilities.setPrivateStaticFinalField(loggerField, logger);
	}

	@Test
	public void testThatCheckedExceptionThrownByCallableIsSwallowedAndLogged() {
		ExceptionUtilities.swallowExceptions(this::callableThrowingCheckedException);
		verify(logger).warning("This exception is a checked exception");
	}

	@Test
	public void testThatValueIsReturnedFromCallableWhenNoExceptionIsThrown() {
		assertThat(ExceptionUtilities.swallowExceptions(() -> 1)).get().isEqualTo(1);
		verifyZeroInteractions(logger);
	}


	/*
	Runnable.run() is not declared to throw checked exception, hence it needs to handled right away, defeating it's purpose here.
	@Test
	public void testThatCheckedExceptionThrownByRunnableIsSwallowedAndLogged() {
		ExceptionUtilities.swallowExceptions(() -> runnableThrowingCheckedException());
		verify(logger).warning("This exception is a checked exception");
	}
	*/

	@Test
	public void testThatExceptionThrownByRunnableIsSwallowedAndLogged() {
		ExceptionUtilities.swallowExceptions(this::runnableThrowingException);
		verify(logger).warning("This exception is NOT a checked exception");
	}

	@Test
	public void testThatRunnableWithoutThrownExceptionExecutesPeacefully() {
		ExceptionUtilities.swallowExceptions(this::runnableNotThrowingException);
		verifyZeroInteractions(logger);
	}


	private int callableThrowingCheckedException() throws IOException {
		throw new IOException("This exception is a checked exception");
	}

	private void runnableThrowingException() {
		throw new RuntimeException("This exception is NOT a checked exception");
	}

	private void runnableNotThrowingException() {

	}
}
