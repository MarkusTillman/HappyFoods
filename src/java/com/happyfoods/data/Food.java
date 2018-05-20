package com.happyfoods.data;


import com.google.common.collect.ImmutableList;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Food implements BusinessData {

	private String name; // todo: unique
	private String description;
	private ImmutableList<Nutrient> nutrients;

	private Food(Builder builder) {
		name = builder.name;
		description = builder.description;
		nutrients = ImmutableList.<Nutrient>builder().addAll(builder.nutrients).build();
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description == null ? "" : description;
	}

	public List<Nutrient> getNutrients() {
		return nutrients;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Food food = (Food) o;
		return Objects.equals(name, food.name) &&
				Objects.equals(description, food.description) &&
				Objects.equals(nutrients, food.nutrients);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, description, nutrients);
	}

	@Override
	public String toString() {
		return "Food{" +
				"name='" + name + '\'' +
				", description='" + description + '\'' +
				", nutrients=" + nutrients +
				'}';
	}

	public static Builder builder(String name) {
		return new Builder(name);
	}

	public Builder buildUpon() {
		return new Builder(name);
	}

	static class Builder {

		private String name;
		private String description;
		private List<Nutrient> nutrients = new LinkedList<>();

		private Builder(String name) {
			this.name = name;
		}

		public Builder description(String description) {
			this.description = description;
			return this;
		}

		public Builder nutrient(Nutrient nutrient) {
			nutrients.add(nutrient);
			return this;
		}

		public Builder nutrients(Nutrient... nutrients) {
			this.nutrients.addAll(Arrays.asList(nutrients));
			return this;
		}

		public Builder nutrients(Collection<Nutrient> nutrients) {
			this.nutrients.addAll(nutrients);
			return this;
		}

		public Food build() {
			return new Food(this);
		}
	}
}
