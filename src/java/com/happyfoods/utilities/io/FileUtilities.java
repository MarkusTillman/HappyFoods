package com.happyfoods.utilities.io;

import com.google.common.base.Strings;
import com.happyfoods.utilities.exception.ExceptionUtilities;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.InputStreamReader;
import java.util.Optional;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;

public class FileUtilities {

	static final Logger logger = getLogger(FileUtilities.class.getSimpleName());
	private static final String SYSTEM_TEMPORARY_DIRECTORY_PATTERN = "%t/";

	public static String getSystemTemporaryDirectoryPattern() {
		return SYSTEM_TEMPORARY_DIRECTORY_PATTERN;
	}

	public static Optional<FileHandler> createFileHandler(String fileName) {
		if (Strings.isNullOrEmpty(fileName)) {
			logger.warning("Cannot create file handler without a name");
			return Optional.empty();
		}
		return Optional.ofNullable(ExceptionUtilities.uncheckThrowable(() -> new FileHandler(fileName))); // TODO: mock when testing.
	}

	public static Optional<InputStreamReader> createStreamReaderFromResourceFile(String fileName) {
		if (Strings.isNullOrEmpty(fileName)) {
			logger.warning("Cannot create file reader without a name");
			return Optional.empty();
		}
		return Optional.ofNullable(FileUtilities.class.getResourceAsStream("/" + fileName)).map(InputStreamReader::new);
	}

	public static Optional<CSVParser> createCsvParser(String fileName, CSVFormat csvFormat) {
		Optional<InputStreamReader> fileReader = createStreamReaderFromResourceFile(fileName);
		return fileReader.map(reader -> ExceptionUtilities.uncheckThrowable(() -> CSVParser.parse(fileReader.get(), csvFormat)));
	}
}
