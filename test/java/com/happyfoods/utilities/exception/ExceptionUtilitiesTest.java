package com.happyfoods.utilities.exception;

import com.happyfoods.utilities.annotation.TagFast;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@TagFast
class ExceptionUtilitiesTest {

	@Test
	void testThatUncheckedExceptionThrownByCallableIsRethrownUnmodified() {
		assertThatThrownBy(() -> ExceptionUtilities.uncheckThrowable(this::callableThrowingUncheckedException))
				.isInstanceOfAny(RuntimeException.class)
				.hasMessage("This is an unchecked exception");
	}

	@Test
	void testThatCheckedExceptionThrownByCallableIsUncheckedAndAndReThrownInARuntimeException() {
		assertThatThrownBy(() -> ExceptionUtilities.uncheckThrowable(this::callableThrowingCheckedException))
				.isInstanceOfAny(RuntimeException.class)
				.hasMessage("java.io.IOException: This is a checked exception");
	}

	@Test
	void testThatValueIsReturnedFromCallableWhenNoExceptionIsThrown() {
		assertThat(ExceptionUtilities.uncheckThrowable(() -> 1)).isEqualTo(1);
	}

	@Test
	void testThatUncheckedExceptionThrownByRunnableIsReThrownUnmodified() {
		assertThatThrownBy(() -> ExceptionUtilities.uncheckThrowable(this::runnableThrowingUncheckedException))
				.isInstanceOfAny(RuntimeException.class, Error.class)
				.hasMessage("This is an unchecked exception");
	}

	/**
	 * Runnable.run() is not declared to throw checked exception, hence it needs to handled right away, defeating it's purpose here.
	 */
	@Test
	void testThatCheckedExceptionThrownByRunnableIsUncheckedAndAndReThrownInARuntimeException() {
//		assertThatThrownBy(() -> ExceptionUtilities.uncheckThrowable(this::runnableThrowingCheckedException))
//				.isInstanceOfAny(RuntimeException.class)
//				.hasMessage("java.io.IOException: This is a checked exception");
	}

	@Test
	void testThatRunnableWithoutThrownExceptionExecutesPeacefully() {
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
