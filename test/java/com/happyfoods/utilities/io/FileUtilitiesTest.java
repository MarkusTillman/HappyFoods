package com.happyfoods.utilities.io;

import com.happyfoods.framework.TestExtension;
import com.happyfoods.utilities.annotation.TagFast;
import com.happyfoods.utilities.reflection.ReflectionUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@TagFast
@ExtendWith(TestExtension.class)
class FileUtilitiesTest {

	@Mock
	private Logger logger;

	@BeforeEach
	void beforeEachTest() {
		Mockito.reset(logger);
		Field loggerField = ReflectionUtilities.getField(FileUtilities.class, "logger").get();
		ReflectionUtilities.setPrivateStaticFinalField(loggerField, logger);
	}

	@Test
	void testThatFileHandlerWithNullNameReturnsEmptyAndLogsWarning() {
		assertThat(FileUtilities.createFileHandler(null)).isEmpty();
		verify(logger).warning("Cannot create file handler without a name");
	}

	@Test
	void testThatFileHandlerWithEmptyNameReturnsEmptyAndLogsWarning() {
		assertThat(FileUtilities.createFileHandler("")).isEmpty();
		verify(logger).warning("Cannot create file handler without a name");
	}

	@Test
	void testThatFileHandlerWithNameCanBeCreated() {
		FileHandler handler = FileUtilities.createFileHandler("fileHandler").get();
		verifyZeroInteractions(logger);
		handler.close(); // todo: remove once test framework deals with this
	}

	@Test
	void testThatSystemTemporaryDirectoryPatternIsPercentageAndTheLetterTEscapedWithSlash() {
		assertThat(FileUtilities.getSystemTemporaryDirectoryPattern()).isEqualTo("%t/");
	}
}
