package com.happyfoods.logging;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.logging.Level;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class LogHandlerTest {

	@Test
	public void testThatLogIsCreatedInTheSystemsTemporaryDirectory() {
		LogHandler.INSTANCE.setupLogging();
	}

	@Test
	public void testThatDefaultLogLevelIsInfo() {
		LogHandler.INSTANCE.setupLogging();
		assertThat(LogHandler.INSTANCE.getLogLevel()).isEqualTo(Level.INFO);
	}

	@Test
	public void testThatLogLevelCanBeChangedAfterSetup() {
		LogHandler.INSTANCE.setupLogging();
		LogHandler.INSTANCE.setLogLevel(Level.FINE);
		assertThat(LogHandler.INSTANCE.getLogLevel()).isEqualTo(Level.FINE);
	}

	@Test
	public void testThatLogNameIsLogAndIsUnderTheSystemTemporaryDirectory() {
		// TODO: setup test framework to save files in memory, not disk.
	}
}
