package com.happyfoods.utilities.io;

import com.happyfoods.utilities.reflection.ReflectionUtilities;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class FileUtilitiesTest {

	@Mock
	private Logger logger;

	@Before
	public void beforeEachTest() {
		Field loggerField = ReflectionUtilities.getField(FileUtilities.class, "logger");
		ReflectionUtilities.setPrivateStaticFinalField(loggerField, logger);
	}

	@Test
	public void testThatFileHandlerWithNullNameReturnsEmptyAndLogsWarning() {
		assertThat(FileUtilities.createFileHandler(null)).isEmpty();
		verify(logger).warning("Cannot create file handler without a name");
	}

	@Test
	public void testThatFileHandlerWithEmptyNameReturnsEmptyAndLogsWarning() {
		assertThat(FileUtilities.createFileHandler("")).isEmpty();
		verify(logger).warning("Cannot create file handler without a name");
	}

	@Test
	public void testThatFileHandlerWithNameCanBeCreated() {
		FileUtilities.createFileHandler("fileHandler").get();
		verifyZeroInteractions(logger);
	}
}
