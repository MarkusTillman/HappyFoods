package com.happyfoods.utilities.io;

import com.happyfoods.framework.TestExtension;
import com.happyfoods.utilities.annotation.TagFast;
import com.happyfoods.utilities.reflection.ReflectionUtilities;
import org.apache.commons.csv.CSVFormat;
import org.assertj.core.util.Files;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.File;
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

	@Nested
	class Miscellaneous {
		@Test
		void testThatSystemTemporaryDirectoryPatternIsPercentageAndTheLetterTEscapedWithSlash() {
			assertThat(FileUtilities.getSystemTemporaryDirectoryPattern()).isEqualTo("%t/");
		}
	}

	@Nested
	class FileHandlerTests {
		@Test
		void testThatCreatingHandlerWithNullNameReturnsEmptyAndLogsWarning() {
			assertThat(FileUtilities.createFileHandler(null)).isEmpty();
			verify(logger).warning("Cannot create file handler without a name");
		}

		@Test
		void testThatCreatingHandlerWithEmptyNameReturnsEmptyAndLogsWarning() {
			assertThat(FileUtilities.createFileHandler("")).isEmpty();
			verify(logger).warning("Cannot create file handler without a name");
		}

		@Test
		void testThatHandlerWithNameCanBeCreated() {
			FileHandler handler = FileUtilities.createFileHandler("fileHandler").get();
			verifyZeroInteractions(logger);
			handler.close(); // todo: remove once test framework deals with this
			Files.delete(new File("fileHandler"));
		}
	}

	@Nested
	class InputStreamReaderTests {
		@Test
		void testThatCreatingReaderWithNullNameReturnsEmptyAndLogsWarning() {
			assertThat(FileUtilities.createStreamReaderFromResourceFile(null)).isEmpty();
			verify(logger).warning("Cannot create file reader without a name");
		}

		@Test
		void testThatCreatingReaderWithEmptyNameReturnsEmptyAndLogsWarning() {
			assertThat(FileUtilities.createStreamReaderFromResourceFile("")).isEmpty();
			verify(logger).warning("Cannot create file reader without a name");
		}

		@Test
		void testThatCreatingReaderFromANonResourceFileReturnsEmptyAndDoesNotLog() {
			assertThat(FileUtilities.createStreamReaderFromResourceFile("NonExisting.resource")).isEmpty();
			verifyZeroInteractions(logger);
		}

		@Test
		void testThatReaderWithNameCanBeCreatedFromResourceFile() {
			FileUtilities.createStreamReaderFromResourceFile("Nutrients.csv").get();
			verifyZeroInteractions(logger);
		}
	}

	@Nested
	class CSVTests {
		@Test
		void testThatCreatingACsvParserUsingFormatDifferentFromFileReturnsOptionalWithValue() {
			assertThat(FileUtilities.createCsvParser("Nutrients.csv", CSVFormat.TDF)).isNotEmpty();
		}

		@Test
		void testThatCreatingACsvParserUsingFormatSameAsFileReturnsOptionalWithValue() {
			CSVFormat csvFormat = CSVFormat.newFormat(',').withCommentMarker('#');
			assertThat(FileUtilities.createCsvParser("Nutrients.csv", csvFormat)).isNotEmpty();
		}
	}
}
