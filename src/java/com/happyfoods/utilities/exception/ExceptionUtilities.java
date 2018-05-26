package com.happyfoods.utilities.exception;

import javax.annotation.Nullable;
import java.util.concurrent.Callable;

public class ExceptionUtilities {

	@Nullable
	public static <T> T uncheckThrowable(Callable<T> callable) {
		try {
			return callable.call();
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
