package com.lcmartins.domain.entities.food;

import com.lcmartins.domain.general.Price;
import com.lcmartins.domain.general.Sellable;
import com.lcmartins.domain.validators.Handler;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class Food extends Sellable {
    private final String name;
    private final Price price;
    private final List<Ingredient> ingredients;
    private final FoodEntityId id;

    private Food(final String name, final Price price, final List<Ingredient> ingredients, final FoodEntityId id) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.ingredients = ingredients;
    }

    public static Food with(final String name, final BigDecimal price, List<Ingredient> ingredient) {
        return new Food(name, Price.with(price), ingredient, null);
    }

    public static Food with(final String name, final BigDecimal price, List<Ingredient> ingredient, final FoodEntityId id) {
        return new Food(name, Price.with(price), ingredient, id);
    }

    public String getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public Ingredient ingredientAt(final int position) {
        return this.ingredients.get(position);
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public FoodEntityId getId() {
        return id;
    }
    @Override
    public void validate(Handler handlerHandler) {
        new FoodValidator(handlerHandler).validate(this);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Food food)) return false;
        return Objects.equals(getId(), food.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPrice(), getIngredients(), getId());
    }
}
