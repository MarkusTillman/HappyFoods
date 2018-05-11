package com.happyfoods.logging;

import org.junit.jupiter.api.Test;

import java.util.logging.Level;

import static org.assertj.core.api.Assertions.assertThat;

class LogHandlerTest {

	@Test
	void testThatLogIsCreatedInTheSystemsTemporaryDirectory() {
		LogHandler.INSTANCE.setupLogging();
	}

	@Test
	void testThatDefaultLogLevelIsInfo() {
		LogHandler.INSTANCE.setupLogging();
		assertThat(LogHandler.INSTANCE.getLogLevel()).isEqualTo(Level.INFO);
	}

	@Test
	void testThatLogLevelCanBeChangedAfterSetup() {
		LogHandler.INSTANCE.setupLogging();
		LogHandler.INSTANCE.setLogLevel(Level.FINE);
		assertThat(LogHandler.INSTANCE.getLogLevel()).isEqualTo(Level.FINE);
	}

	@Test
	void testThatLogNameIsLogAndIsUnderTheSystemTemporaryDirectory() {
		// TODO: setup test framework to save files in memory, not disk.
	}
}
