package com.happyfoods.data;

import com.happyfoods.utilities.annotation.TagFast;
import com.happyfoods.utilities.annotation.TagSmoke;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@TagFast
@TagSmoke
class FoodTest {

	@Test
	void testThatFoodRequiresAName() {
		assertThat(Food.builder("Required name").build().getName()).isEqualTo("Required name");
	}

	@Test
	void testThatDescriptionIsEmptyByDefault() {
		assertThat(Food.builder("name").build().getDescription()).isEmpty();
	}

	@Test
	void testThatFoodCanHaveADescription() {
		assertThat(Food.builder("name").description("Description").build().getDescription()).isEqualTo("Description");
	}

	@Test
	void testThatASingleNutrientCanBeAdded() {
		Nutrient nutrient = mock(Nutrient.class);
		assertThat(Food.builder("name").nutrient(nutrient).build().getNutrients()).containsOnly(nutrient);
	}

	@Test
	void testThatASeveralNutrientsCanBeAddedAtOnce() {
		Nutrient nutrient = mock(Nutrient.class);
		assertThat(Food.builder("name").nutrients(nutrient, nutrient).build().getNutrients()).containsExactly(nutrient, nutrient);
	}

	@Test
	void testThatASeveralNutrientsCanBeAddedAtOnceUsingAList() {
		Nutrient nutrient = mock(Nutrient.class);
		List<Nutrient> nutrients = Arrays.asList(nutrient, nutrient);
		assertThat(Food.builder("name").nutrients(nutrients).build().getNutrients()).containsAll(nutrients);
	}

	@Test
	void testThatNewObjectIsCreatedWhenBuildingUponCreatedObject() {
		Food food = Food.builder("name").build();
		Food newFood = food.buildUpon().build();
		assertThat(newFood).isNotSameAs(food).isEqualTo(food);
	}

	@Test
	void testThatDescriptionCanBeAddedAfterConstruction() {
		Food food = Food.builder("name").build();
		Food foodWithDescription = food.buildUpon().description("description").build();
		assertThat(foodWithDescription.getDescription()).isEqualTo("description");
	}

	@Test
	void testThatNutrientCanBeAddedAfterConstruction() {
		Food food = Food.builder("name").build();
		Nutrient nutrient = mock(Nutrient.class);
		Food foodWithNutrient = food.buildUpon().nutrient(nutrient).build();
		assertThat(foodWithNutrient.getNutrients()).containsOnly(nutrient);
	}
}
