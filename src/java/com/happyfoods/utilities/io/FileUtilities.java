package com.happyfoods.utilities.io;

import com.google.common.base.Strings;
import com.happyfoods.utilities.exception.ExceptionUtilities;

import java.util.Optional;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;

public class FileUtilities {

	private static final Logger logger = getLogger(FileUtilities.class.getSimpleName());

	public static Optional<FileHandler> createFileHandler(String logFileName) {
		if (Strings.isNullOrEmpty(logFileName)) {
			logger.warning("Cannot create file handler without a name");
			return Optional.empty();
		}
		return ExceptionUtilities.swallowExceptions(() -> new FileHandler(logFileName)); // TODO: mock when testing.
	}
}
