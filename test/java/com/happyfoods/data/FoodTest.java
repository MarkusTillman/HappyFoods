package com.happyfoods.data;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FoodTest {

	@Test
	public void testThatFoodRequiresAName() {
		assertThat(Food.builder("Required name").build().getName()).isEqualTo("Required name");
	}

	@Test
	public void testThatDescriptionIsEmptyByDefault() {
		assertThat(Food.builder("name").build().getDescription()).isEmpty();
	}

	@Test
	public void testThatFoodCanHaveADescription() {
		assertThat(Food.builder("name").description("Description").build().getDescription()).isEqualTo("Description");
	}

	@Test
	public void testThatASingleNutrientCanBeAdded() {
		Nutrient nutrient = Mockito.mock(Nutrient.class);
		assertThat(Food.builder("name").nutrient(nutrient).build().getNutrients()).containsOnly(nutrient);
	}

	@Test
	public void testThatASeveralNutrientsCanBeAddedAtOnce() {
		Nutrient nutrient = Mockito.mock(Nutrient.class);
		assertThat(Food.builder("name").nutrients(nutrient, nutrient).build().getNutrients()).containsExactly(nutrient, nutrient);
	}

	@Test
	public void testThatASeveralNutrientsCanBeUsingAList() {
		Nutrient nutrient = Mockito.mock(Nutrient.class);
		List<Nutrient> nutrients = Arrays.asList(nutrient, nutrient);
		assertThat(Food.builder("name").nutrients(nutrients).build().getNutrients()).containsAll(nutrients);
	}
}
