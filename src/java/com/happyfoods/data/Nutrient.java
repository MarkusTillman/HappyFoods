package com.happyfoods.data;

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
		name = builder.name;
		description = builder.description;
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
		return description == null ? "" : description;
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

	static Builder builder(String name) {
		return new Builder(name);
	}

	static class Builder {
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

		public Builder description(String description) {
			this.description = description;
			return this;
		}

		public Builder causesInflammation(boolean causesInflammation) {
			this.causesInflammation = causesInflammation;
			return this;
		}

		public Builder causesHeartDisease(boolean causesHeartDisease) {
			this.causesHeartDisease = causesHeartDisease;
			return this;
		}

		public Builder causesMentalIllness(boolean causesMentalIllness) {
			this.causesMentalIllness = causesMentalIllness;
			return this;
		}

		public Builder causesDepression(boolean causesDepression) {
			this.causesDepression = causesDepression;
			return this;
		}

		public Builder causesStress(boolean causesStress) {
			this.causesStress = causesStress;
			return this;
		}

		public Builder causesObesity(boolean causesObesity) {
			this.causesObesity = causesObesity;
			return this;
		}

		public Builder causesOxidation(boolean causesOxidation) {
			this.causesOxidation = causesOxidation;
			return this;
		}

		public Nutrient build() {
			return new Nutrient(this);
		}
	}
}
