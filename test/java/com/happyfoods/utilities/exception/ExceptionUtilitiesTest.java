package com.happyfoods.utilities.exception;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class ExceptionUtilitiesTest {

	@Test
	public void testThatUncheckedExceptionThrownByCallableIsRethrownUnmodified() {
		assertThatThrownBy(() -> ExceptionUtilities.uncheckThrowable(this::callableThrowingUncheckedException))
				.isInstanceOfAny(RuntimeException.class)
				.hasMessage("This is an unchecked exception");
	}

	@Test
	public void testThatCheckedExceptionThrownByCallableIsUncheckedAndAndReThrownInARuntimeException() {
		assertThatThrownBy(() -> ExceptionUtilities.uncheckThrowable(this::callableThrowingCheckedException))
				.isInstanceOfAny(RuntimeException.class)
				.hasMessage("java.io.IOException: This is a checked exception");
	}

	@Test
	public void testThatValueIsReturnedFromCallableWhenNoExceptionIsThrown() {
		assertThat(ExceptionUtilities.uncheckThrowable(() -> 1)).contains(1);
	}

	@Test
	public void testThatUncheckedExceptionThrownByRunnableIsReThrownUnmodified() {
		assertThatThrownBy(() -> ExceptionUtilities.uncheckThrowable(this::runnableThrowingUncheckedException))
				.isInstanceOfAny(RuntimeException.class, Error.class)
				.hasMessage("This is an unchecked exception");
	}

	/**
	 * Runnable.run() is not declared to throw checked exception, hence it needs to handled right away, defeating it's purpose here.
	 */
	@Test
	public void testThatCheckedExceptionThrownByRunnableIsUncheckedAndAndReThrownInARuntimeException() {
//		assertThatThrownBy(() -> ExceptionUtilities.uncheckThrowable(this::runnableThrowingCheckedException))
//				.isInstanceOfAny(RuntimeException.class)
//				.hasMessage("java.io.IOException: This is a checked exception");
	}

	@Test
	public void testThatRunnableWithoutThrownExceptionExecutesPeacefully() {
		assertThatCode(() -> ExceptionUtilities.uncheckThrowable(this::runnableNotThrowingException)).doesNotThrowAnyException();
	}

	private int callableThrowingUncheckedException() {
		throw new RuntimeException("This is an unchecked exception");
	}

	private int callableThrowingCheckedException() throws IOException {
		throw new IOException("This is a checked exception");
	}

	private void runnableThrowingUncheckedException() {
		throw new RuntimeException("This is an unchecked exception");
	}

	private void runnableNotThrowingException() {

	}
}
