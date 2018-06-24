package com.happyfoods.database;

import com.happyfoods.data.Nutrient;
import com.happyfoods.utilities.annotation.TagSmoke;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TagSmoke
class NutrientControllerTest {

	@Test
	void testThatValuesAreProperlyLoaded() {
		Nutrient longOmega3 = Nutrient.builder("Long omega-3")
				.causesInflammation(false)
				.causesHeartDisease(false)
				.causesMentalIllness(false)
				.build();
		Nutrient shortOmega3 = Nutrient.builder("Short omega-3")
				.causesInflammation(false)
				.causesHeartDisease(false)
				.causesMentalIllness(false)
				.description("Less effective than long omega-3").build();
		Nutrient cholesterolLdl = Nutrient.builder("Cholesterol LDL")
				.causesHeartDisease(true)
				.build();

		List<Nutrient> nutrients = new NutrientController().loadAll();

		assertThat(nutrients).contains(longOmega3, shortOmega3, cholesterolLdl);
	}
}