package com.happyfoods.data;

import com.happyfoods.utilities.annotation.TagFast;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TagFast
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
		Nutrient nutrient = Mockito.mock(Nutrient.class);
		assertThat(Food.builder("name").nutrient(nutrient).build().getNutrients()).containsOnly(nutrient);
	}

	@Test
	void testThatASeveralNutrientsCanBeAddedAtOnce() {
		Nutrient nutrient = Mockito.mock(Nutrient.class);
		assertThat(Food.builder("name").nutrients(nutrient, nutrient).build().getNutrients()).containsExactly(nutrient, nutrient);
	}

	@Test
	void testThatASeveralNutrientsCanBeUsingAList() {
		Nutrient nutrient = Mockito.mock(Nutrient.class);
		List<Nutrient> nutrients = Arrays.asList(nutrient, nutrient);
		assertThat(Food.builder("name").nutrients(nutrients).build().getNutrients()).containsAll(nutrients);
	}
}
