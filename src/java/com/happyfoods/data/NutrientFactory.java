package com.happyfoods.data;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class NutrientFactory { // todo: common class for Food & Nutrient factories?
	private static List<Nutrient> nutrients = new LinkedList<>();

	public static Nutrient createNutrient(Nutrient.Builder nutrientBuilder) {
		Nutrient newNutrient = nutrientBuilder.build();
		if (nutrientWithNameAlreadyExists(newNutrient)) {
			throw new IllegalArgumentException(String.format("'%s' already exists, use the update-method?", newNutrient.getName()));
		}
		nutrients.add(newNutrient);
		return newNutrient;
	}

	private static boolean nutrientWithNameAlreadyExists(Nutrient newNutrient) {
		return nutrients.parallelStream().map(Nutrient::getName).anyMatch(existingNutrientName -> existingNutrientName.equals(newNutrient.getName()));
	}

	public static Optional<Nutrient> updateNutrient(Nutrient nutrientWithUpdatedValues) {
		Optional<Nutrient> nutrientToUpdate = nutrients.parallelStream().filter(nutrient -> nutrient.getName().equals(nutrientWithUpdatedValues.getName())).findFirst();
		if (nutrientToUpdate.isPresent()) {
			replaceNutrient(nutrientWithUpdatedValues);
			return Optional.of(nutrientWithUpdatedValues);
		} else {
			return Optional.empty();
		}
	}

	private static void replaceNutrient(Nutrient nutrientWithUpdatedValues) {
		nutrients.replaceAll(nutrient -> {
			if (nutrient.getName().equals(nutrientWithUpdatedValues.getName())) {
				return nutrientWithUpdatedValues;
			}
			return nutrient;
		});
	}
}
