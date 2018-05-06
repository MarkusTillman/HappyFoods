package com.happyfoods.utilities;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Map.Entry;

public final class CollectionUtilities {

	private CollectionUtilities() {
	}

	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
		return sortMap(map, Entry.comparingByValue());
	}

	public static <K, V extends Comparable<? super V>> Map<K, V> sortReversedByValue(Map<K, V> map) {
		return sortMap(map, Entry.comparingByValue(Collections.reverseOrder()));
	}

	public static <K, V extends Comparable<? super V>> Map<K, V> sortMap(Map<K, V> map, Comparator<? super Entry<K, V>> comparator) {
		return map.entrySet()
				.stream()
				.sorted(comparator)
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue, (key, value) -> key, LinkedHashMap::new));
	}
}
