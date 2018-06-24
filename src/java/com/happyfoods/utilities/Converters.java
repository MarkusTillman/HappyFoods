package com.happyfoods.utilities;

import javax.annotation.Nullable;

public class Converters {

	public static class BooleanConverter {
		public static @Nullable Boolean toBoolean(@Nullable String string) {
			if (string == null) {
				return null;
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
					trimmedString.equalsIgnoreCase("false");
		}

		private static boolean isTrue(String trimmedString) {
			return trimmedString.equalsIgnoreCase("yes") ||
					trimmedString.equals("1") ||
					trimmedString.equalsIgnoreCase("true");
		}
	}
}
