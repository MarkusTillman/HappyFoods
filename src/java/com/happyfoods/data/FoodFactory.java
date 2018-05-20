package com.happyfoods.data;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class FoodFactory {
	private static List<Food> foods = new LinkedList<>();

	public static Food createFood(Food.Builder foodBuilder) {
		Food newFood = foodBuilder.build();
		if (foodWithNameAlreadyExists(newFood)) {
			throw new IllegalArgumentException(String.format("'%s' already exists, use the update-method?", newFood.getName()));
		}
		foods.add(newFood);
		return newFood;
	}

	private static boolean foodWithNameAlreadyExists(Food newFood) {
		return foods.parallelStream().map(Food::getName).anyMatch(existingFoodName -> existingFoodName.equals(newFood.getName()));
	}

	public static Optional<Food> updateFood(Food foodWithUpdatedValues) {
		Optional<Food> foodToUpdate = foods.parallelStream().filter(food -> food.getName().equals(foodWithUpdatedValues.getName())).findFirst();
		if (foodToUpdate.isPresent()) {
			replaceFood(foodWithUpdatedValues);
			return Optional.of(foodWithUpdatedValues);
		} else {
			return Optional.empty();
		}
	}

	private static void replaceFood(Food foodWithUpdatedValues) {
		foods.replaceAll(food -> {
			if (food.getName().equals(foodWithUpdatedValues.getName())) {
				return foodWithUpdatedValues;
			}
			return food;
		});
	}
}
