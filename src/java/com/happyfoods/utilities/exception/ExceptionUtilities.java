package com.happyfoods.utilities.exception;

import java.util.Optional;
import java.util.concurrent.Callable;

public class ExceptionUtilities {

	public static <T> Optional<T> uncheckThrowable(Callable<T> callable) {
		try {
			return Optional.ofNullable(callable.call());
		} catch (Error | RuntimeException e) {
			throw e;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	public static void uncheckThrowable(Runnable runnable) {
		try {
			runnable.run();
		} catch (Error | RuntimeException e) {
			throw e;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}
}
