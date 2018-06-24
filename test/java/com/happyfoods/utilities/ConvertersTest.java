package com.happyfoods.utilities;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.happyfoods.utilities.Converters.BooleanConverter;
import static org.assertj.core.api.Assertions.assertThat;

class ConvertersTest {

	@Nested
	class BooleanConversions {
		@Nested
		class Miscellaneous {
			@Test
			void testThatNullStringIsConvertedToFalse() {
				assertThat(BooleanConverter.toBoolean(null)).isFalse();
			}

			@Test
			void testThatEmptyStringIsConvertedToFalse() {
				assertThat(BooleanConverter.toBoolean("")).isFalse();
			}

			@Test
			void testThatBooleanConversionIsCaseInsensitive() {
				assertThat(BooleanConverter.toBoolean("no")).isFalse();
				assertThat(BooleanConverter.toBoolean("NO")).isFalse();
			}
		}

		@Nested
		class Numbers {
			@Test
			void testThatZeroAsStringIsConvertedToFalse() {
				assertThat(BooleanConverter.toBoolean("0")).isFalse();
			}

			@Test
			void testThatOneAsStringIsConvertedToTrue() {
				assertThat(BooleanConverter.toBoolean("1")).isTrue();
			}

			@Test
			void testThatTwoAsStringIsNotConverted() {
				assertThat(BooleanConverter.toBoolean("2")).isNull();
			}
		}

		@Nested
		class Words {
			@Test
			void testThatTheWordNoIsConvertedToFalse() {
				assertThat(BooleanConverter.toBoolean("No")).isFalse();
			}

			@Test
			void testThatTheWordYesIsConvertedToTrue() {
				assertThat(BooleanConverter.toBoolean("Yes")).isTrue();
			}

			@Test
			void testThatTheWordFalseIsConvertedToFalse() {
				assertThat(BooleanConverter.toBoolean("False")).isFalse();
			}

			@Test
			void testThatTheWordTrueIsConvertedToTrue() {
				assertThat(BooleanConverter.toBoolean("True")).isTrue();
			}
		}
	}
}