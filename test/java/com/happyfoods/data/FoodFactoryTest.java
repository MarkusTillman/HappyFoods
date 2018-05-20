package com.happyfoods.data;

import com.happyfoods.utilities.annotation.TagFast;
import com.happyfoods.utilities.annotation.TagSmoke;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@TagFast
@TagSmoke
class FoodFactoryTest {

	@Test
	void testThatFoodNamesMustBeUnique() {
		Food.Builder foodBuilder = Food.builder("NonUniqueName");
		FoodFactory.createFood(foodBuilder);
		assertThatThrownBy(() -> FoodFactory.createFood(foodBuilder))
				.hasMessageContaining("'NonUniqueName' already exists, use the update-method?");
	}

	@Test
	void testThatUpdatingExistingFoodReturnsUpdatedObject() {
		Food asparagus = FoodFactory.createFood(Food.builder("Asparagus"));
		Food asparagusWithDescription = asparagus.buildUpon()
				.description("Is a spring vegetable, a flowering perennial plant species in the genus Asparagus")
				.build();
		assertThat(FoodFactory.updateFood(asparagusWithDescription).get().getDescription())
				.isEqualTo("Is a spring vegetable, a flowering perennial plant species in the genus Asparagus");
	}

	@Test
	void testThatUpdatingNonExistingFoodReturnsEmpty() {
		Food nonExistingFood = Food.builder("NonExisting").build();
		assertThat(FoodFactory.updateFood(nonExistingFood)).isEmpty();
	}
}