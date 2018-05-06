package com.happyfoods.logging;

import com.happyfoods.utilities.io.FileUtilities;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public enum LogHandler {
	INSTANCE;

	private static final Level DEFAULT_LOG_LEVEL = Level.INFO;

	public void setupLogging() {
		FileHandler logFileHandler = createLogFileInSystemTemporaryDirectory("Log.txt");
		logFileHandler.setFormatter(new SimpleFormatter());
		addRootLoggerToFileHandler(logFileHandler);
	}

	private static FileHandler createLogFileInSystemTemporaryDirectory(String logName) {
		return FileUtilities.createFileHandler(FileUtilities.getSystemTemporaryDirectoryPattern() + logName)
				.orElseThrow(() -> new RuntimeException("Could not create log file"));
	}

	private static void addRootLoggerToFileHandler(FileHandler fileHandler) {
		Logger logger = getRootLogger();
		logger.addHandler(fileHandler);
		logger.setLevel(DEFAULT_LOG_LEVEL);
	}

	private static Logger getRootLogger() {
		return Logger.getLogger("");
	}

	public Level getLogLevel() {
		return getRootLogger().getLevel();
	}

	public void setLogLevel(Level level) {
		getRootLogger().setLevel(level);
	}
}
