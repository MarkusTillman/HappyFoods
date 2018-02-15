package com.happyfoods.utilities.exception;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;

public class ExceptionUtilities {

	private static final Logger logger = getLogger(ExceptionUtilities.class.getSimpleName());

	public static <T> Optional<T> swallowExceptions(Callable<T> callable) {
		try {
			return Optional.ofNullable(callable.call());
		} catch (Exception e) {
			logger.warning(e.getMessage());
			return Optional.empty();
		}
	}

	public static void swallowExceptions(Runnable runnable) {
		try {
			runnable.run();
		} catch (Exception e) {
			logger.warning(e.getMessage());
		}
	}
}
