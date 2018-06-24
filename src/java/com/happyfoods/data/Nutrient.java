package com.happyfoods.data;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

public class Nutrient implements BusinessData {

	private String name;
	private String description;
	private Boolean causesInflammation;
	private Boolean causesHeartDisease;
	private Boolean causesMentalIllness;
	private Boolean causesDepression;
	private Boolean causesStress;
	private Boolean causesObesity;
	private Boolean causesOxidation;

	private Nutrient(Builder builder) {
		name = builder.name == null ? "" : builder.name;
		description = builder.description == null ? "" : builder.description;
		causesInflammation = builder.causesInflammation;
		causesHeartDisease = builder.causesHeartDisease;
		causesMentalIllness = builder.causesMentalIllness;
		causesDepression = builder.causesDepression;
		causesStress = builder.causesStress;
		causesObesity = builder.causesObesity;
		causesOxidation = builder.causesOxidation;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Optional<Boolean> causesInflammation() {
		return Optional.ofNullable(causesInflammation);
	}

	public Optional<Boolean> causesHeartDisease() {
		return Optional.ofNullable(causesHeartDisease);
	}

	public Optional<Boolean> causesMentalIllness() {
		return Optional.ofNullable(causesMentalIllness);
	}

	public Optional<Boolean> causesDepression() {
		return Optional.ofNullable(causesDepression);
	}

	public Optional<Boolean> causesStress() {
		return Optional.ofNullable(causesStress);
	}

	public Optional<Boolean> causesObesity() {
		return Optional.ofNullable(causesObesity);
	}

	public Optional<Boolean> causesOxidation() {
		return Optional.ofNullable(causesOxidation);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Nutrient nutrient = (Nutrient) o;
		return Objects.equals(name, nutrient.name) &&
				Objects.equals(description, nutrient.description) &&
				Objects.equals(causesInflammation, nutrient.causesInflammation) &&
				Objects.equals(causesHeartDisease, nutrient.causesHeartDisease) &&
				Objects.equals(causesMentalIllness, nutrient.causesMentalIllness) &&
				Objects.equals(causesDepression, nutrient.causesDepression) &&
				Objects.equals(causesStress, nutrient.causesStress) &&
				Objects.equals(causesObesity, nutrient.causesObesity) &&
				Objects.equals(causesOxidation, nutrient.causesOxidation);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, description, causesInflammation, causesHeartDisease, causesMentalIllness, causesDepression, causesStress, causesObesity, causesOxidation);
	}

	@Override
	public String toString() {
		return "Nutrient{" +
				"name='" + name + '\'' +
				", description='" + description + '\'' +
				", causesInflammation=" + causesInflammation +
				", causesHeartDisease=" + causesHeartDisease +
				", causesMentalIllness=" + causesMentalIllness +
				", causesDepression=" + causesDepression +
				", causesStress=" + causesStress +
				", causesObesity=" + causesObesity +
				", causesOxidation=" + causesOxidation +
				'}';
	}

	public static Builder builder(String name) {
		return new Builder(name);
	}

	public Builder buildUpon() {
		return new Builder(name);
	}

	public static class Builder {
		private String name;
		private String description;
		private Boolean causesInflammation;
		private Boolean causesHeartDisease;
		private Boolean causesMentalIllness;
		private Boolean causesDepression;
		private Boolean causesStress;
		private Boolean causesObesity;
		private Boolean causesOxidation;

		private Builder(String name) {
			this.name = name;
		}

		public Builder description(@Nullable String description) {
			this.description = description;
			return this;
		}

		public Builder causesInflammation(@Nullable Boolean causesInflammation) {
			this.causesInflammation = causesInflammation;
			return this;
		}

		public Builder causesHeartDisease(@Nullable Boolean causesHeartDisease) {
			this.causesHeartDisease = causesHeartDisease;
			return this;
		}

		public Builder causesMentalIllness(@Nullable Boolean causesMentalIllness) {
			this.causesMentalIllness = causesMentalIllness;
			return this;
		}

		public Builder causesDepression(@Nullable Boolean causesDepression) {
			this.causesDepression = causesDepression;
			return this;
		}

		public Builder causesStress(@Nullable Boolean causesStress) {
			this.causesStress = causesStress;
			return this;
		}

		public Builder causesObesity(@Nullable Boolean causesObesity) {
			this.causesObesity = causesObesity;
			return this;
		}

		public Builder causesOxidation(@Nullable Boolean causesOxidation) {
			this.causesOxidation = causesOxidation;
			return this;
		}

		public Nutrient build() {
			return new Nutrient(this);
		}
	}
}
