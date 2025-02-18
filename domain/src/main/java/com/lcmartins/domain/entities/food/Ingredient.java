package com.lcmartins.domain.entities.food;

import com.lcmartins.domain.general.ComparableValue;



public class Ingredient extends ComparableValue<Integer> {
    private final String name;
    private final Integer quantityInStock;

    private Ingredient(String name, Integer quantityInStock) {
        super(quantityInStock);
        this.name = name;
        this.quantityInStock = quantityInStock;
    }

    public static Ingredient with(final String name,
                                  final Integer quantityInStock) {
        return new Ingredient(name, quantityInStock);
    }

    public boolean isValid() {
        return !this.name.isBlank() && !this.isLessThan(10);
    }

    public String getName() {
        return name;
    }

    public Integer getQuantityInStock() {
        return quantityInStock;
    }
}
