package com.happyfoods.utilities;

import com.google.common.base.Strings;

import javax.annotation.Nullable;

public class Converters {

	public static class BooleanConverter {
		public static @Nullable Boolean toBoolean(@Nullable String string) {
			if (Strings.isNullOrEmpty(string)) {
				return false;
			}
			String trimmedString = string.trim();
			if (isFalse(trimmedString)) {
				return false;
			} else if (isTrue(trimmedString)) {
				return true;
			}
			return null;
		}

		private static boolean isFalse(String trimmedString) {
			return trimmedString.equalsIgnoreCase("no") ||
					trimmedString.equals("0") ||
					trimmedString.equalsIgnoreCase("false") ||
					Strings.isNullOrEmpty(trimmedString);
		}

		private static boolean isTrue(String trimmedString) {
			return trimmedString.equalsIgnoreCase("yes") ||
					trimmedString.equals("1") ||
					trimmedString.equalsIgnoreCase("true");
		}
	}
}
