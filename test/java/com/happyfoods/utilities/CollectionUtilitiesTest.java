package com.happyfoods.utilities;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CollectionUtilitiesTest {

	private static final ImmutableMap<String, Integer> keysWithUnsortedValues = ImmutableMap.of(
			"a", 2,
			"b", 3,
			"c", 1);

	@Test
	public void testThatValuesInMappedCanBeSortedInOrder() {
		Map<String, Integer> sortedMap = CollectionUtilities.sortByValue(keysWithUnsortedValues);
		Iterator<Map.Entry<String, Integer>> iterator = sortedMap.entrySet().iterator();
		assertThat(iterator.next().getValue()).isEqualTo(1);
		assertThat(iterator.next().getValue()).isEqualTo(2);
		assertThat(iterator.next().getValue()).isEqualTo(3);
	}

	@Test
	public void testThatValuesInMappedCanBeSortedReverseInOrder() {
		Map<String, Integer> sortedMap = CollectionUtilities.sortReversedByValue(keysWithUnsortedValues);
		Iterator<Map.Entry<String, Integer>> iterator = sortedMap.entrySet().iterator();
		assertThat(iterator.next().getValue()).isEqualTo(3);
		assertThat(iterator.next().getValue()).isEqualTo(2);
		assertThat(iterator.next().getValue()).isEqualTo(1);
	}

	@Test
	public void testThatCustomSorterCanBeUsedToSortMap() {
		Map<String, Integer> sortedMap = CollectionUtilities.sortMap(keysWithUnsortedValues, Map.Entry.comparingByKey(Comparator.reverseOrder()));
		Iterator<Map.Entry<String, Integer>> iterator = sortedMap.entrySet().iterator();
		assertThat(iterator.next().getKey()).isEqualTo("c");
		assertThat(iterator.next().getKey()).isEqualTo("b");
		assertThat(iterator.next().getKey()).isEqualTo("a");
	}
}

