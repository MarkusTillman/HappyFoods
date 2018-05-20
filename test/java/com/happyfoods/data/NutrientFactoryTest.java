package com.happyfoods.data;

import com.happyfoods.utilities.annotation.TagFast;
import com.happyfoods.utilities.annotation.TagSmoke;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@TagFast
@TagSmoke
class NutrientFactoryTest {

	@Test
	void testThatNutrientNamesMustBeUnique() {
		Nutrient.Builder nutrientBuilder = Nutrient.builder("NonUniqueName");
		NutrientFactory.createNutrient(nutrientBuilder);
		assertThatThrownBy(() -> NutrientFactory.createNutrient(nutrientBuilder))
				.hasMessageContaining("'NonUniqueName' already exists, use the update-method?");
	}

	@Test
	void testThatUpdatingExistingNutrientReturnsUpdatedObject() {
		Nutrient vitaminA = NutrientFactory.createNutrient(Nutrient.builder("Vitamin-A"));
		Nutrient vitaminAWithDescription = vitaminA.buildUpon()
				.description("Is a group of unsaturated nutritional organic compounds")
				.build();
		assertThat(NutrientFactory.updateNutrient(vitaminAWithDescription).get().getDescription())
				.isEqualTo("Is a group of unsaturated nutritional organic compounds");
	}

	@Test
	void testThatUpdatingNonExistingNutrientReturnsEmpty() {
		Nutrient nonExistingNutrient = Nutrient.builder("NonExisting").build();
		assertThat(NutrientFactory.updateNutrient(nonExistingNutrient)).isEmpty();
	}
}