package com.happyfoods.data;

import com.happyfoods.utilities.annotation.TagFast;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@TagFast
class NutrientTest {

	@Test
	void testThatNutrientRequiresAName() {
		assertThat(Nutrient.builder("Required name").build().getName()).isEqualTo("Required name");
	}

	@Test
	void testThatDescriptionIsEmptyByDefault() {
		assertThat(Nutrient.builder("name").build().getDescription()).isEmpty();
	}

	@Test
	void testThatNutrientCanHaveADescription() {
		assertThat(Nutrient.builder("name").description("Description").build().getDescription()).isEqualTo("Description");
	}

	@Test
	void testThatCausesByDefaultAreEmpty() {
		Nutrient nutrientWithoutEffects = Nutrient.builder("name").build();
		assertThat(nutrientWithoutEffects.causesInflammation()).isEmpty();
		assertThat(nutrientWithoutEffects.causesHeartDisease()).isEmpty();
		assertThat(nutrientWithoutEffects.causesMentalIllness()).isEmpty();
		assertThat(nutrientWithoutEffects.causesDepression()).isEmpty();
		assertThat(nutrientWithoutEffects.causesStress()).isEmpty();
		assertThat(nutrientWithoutEffects.causesObesity()).isEmpty();
		assertThat(nutrientWithoutEffects.causesOxidation()).isEmpty();
	}

	@Test
	void testThatNutrientCanCauseInflammation() {
		assertThat(Nutrient.builder("name").causesInflammation(true).build().causesInflammation()).contains(true);
	}

	@Test
	void testThatNutrientCanCauseHeartDisease() {
		assertThat(Nutrient.builder("name").causesHeartDisease(true).build().causesHeartDisease()).contains(true);
	}

	@Test
	void testThatNutrientCanCauseMentalIllness() {
		assertThat(Nutrient.builder("name").causesMentalIllness(true).build().causesMentalIllness()).contains(true);
	}

	@Test
	void testThatNutrientCanCauseDepression() {
		assertThat(Nutrient.builder("name").causesDepression(true).build().causesDepression()).contains(true);
	}

	@Test
	void testThatNutrientCanCauseStress() {
		assertThat(Nutrient.builder("name").causesStress(true).build().causesStress()).contains(true);
	}

	@Test
	void testThatNutrientCanCauseObesity() {
		assertThat(Nutrient.builder("name").causesObesity(true).build().causesObesity()).contains(true);
	}

	@Test
	void testThatNutrientCanCauseOxidation() {
		assertThat(Nutrient.builder("name").causesOxidation(true).build().causesOxidation()).contains(true);
	}
}
