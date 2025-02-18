package com.lcmartins.orders.food;

import com.lcmartins.domain.entities.food.Food;
import com.lcmartins.domain.entities.food.Ingredient;
import com.lcmartins.domain.exceptions.DomainException;
import com.lcmartins.domain.validators.ThrowsErrorValidatorHandler;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FoodTest {

    @Test
    public void givenValidNameAndValue_whenCallsValidate_thenShouldNotReturnError() {
        final var expectedName = "hamburger";
        final var expectedPrice = BigDecimal.valueOf(100.33353).setScale(2, RoundingMode.HALF_EVEN);
        final var expectedIngredientName = "pao";
        final var expectedQuantityInStock = 10;
        final List<Ingredient> expectedIngredient = new ArrayList<>();
        expectedIngredient.add(Ingredient.with("pao", expectedQuantityInStock));
        Food food = Food.with(expectedName, expectedPrice, expectedIngredient);

        food.validate(new ThrowsErrorValidatorHandler());

        assertNotNull(food);
        assertEquals(expectedName, food.getName());
        assertEquals(expectedPrice, food.getPrice().getValue());
        assertEquals(expectedQuantityInStock, food.ingredientAt(0).getQuantityInStock());
        assertEquals(expectedIngredientName, food.ingredientAt(0).getName());
    }

    @Test
    public void givenInvalidPrice_whenCallsValidate_thenShouldReturnError() {
        final String expectedName = "hamburger";
        final var expectedValue = BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_EVEN);
        final var expectedErrorMesssage = "price 0.00 is not valid";
        final var expectedIngredientName = "pao";
        final var expectedQuantityInStock = 10;
        final List<Ingredient> expectedIngredient = new ArrayList<>();
        expectedIngredient.add(Ingredient.with("pao", expectedQuantityInStock));
        Food food = Food.with(expectedName, expectedValue, expectedIngredient);

        final var expectedError = assertThrows(DomainException.class, () -> food.validate(new ThrowsErrorValidatorHandler()));

        assertNotNull(food);
        assertEquals(expectedErrorMesssage, expectedError.getMessage());
        assertEquals(expectedName, food.getName());
        assertEquals(expectedValue, food.getPrice().getValue());
        assertEquals(expectedIngredientName, food.ingredientAt(0).getName());
        assertEquals(expectedQuantityInStock, food.ingredientAt(0).getQuantityInStock());
    }


    @Test
    public void givenInvalidName_whenCallsValidate_thenShouldReturnError() {
        final String expectedName = "  ";
        final var expectedValue = BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_EVEN);
        final var expectedErrorMesssage = "name cannot be null or empty";
        final var expectedIngredientName = "pao";
        final var expectedQuantityInStock = 10;
        final List<Ingredient> expectedIngredient = new ArrayList<>();
        expectedIngredient.add(Ingredient.with("pao", expectedQuantityInStock));
        Food food = Food.with(expectedName, expectedValue, expectedIngredient);

        final var expectedError = assertThrows(DomainException.class, () -> food.validate(new ThrowsErrorValidatorHandler()));

        assertNotNull(food);
        assertEquals(expectedErrorMesssage, expectedError.getMessage());
        assertEquals(expectedName, food.getName());
        assertEquals(expectedValue, food.getPrice().getValue());
        assertEquals(expectedIngredientName, food.ingredientAt(0).getName());
        assertEquals(expectedQuantityInStock, food.ingredientAt(0).getQuantityInStock());
    }

    @Test
    public void givenInvalidEmptyIngredients_whenCallsValidate_thenShouldReturnError() {
        final String expectedName = "hamburger";
        final var expectedValue = BigDecimal.valueOf(100).setScale(2, RoundingMode.HALF_EVEN);
        final var expectedErrorMesssage = "check the ingredientes, they can be in invalid state";
        final List<Ingredient> expectedIngredient = new ArrayList<>();
        Food food = Food.with(expectedName, expectedValue, expectedIngredient);

        final var expectedError = assertThrows(DomainException.class, () -> food.validate(new ThrowsErrorValidatorHandler()));

        assertNotNull(food);
        assertEquals(expectedErrorMesssage, expectedError.getMessage());
        assertEquals(expectedName, food.getName());
        assertEquals(expectedValue, food.getPrice().getValue());
    }


    @Test
    public void givenInvalidNullIngredients_whenCallsValidate_thenShouldReturnError() {
        final String expectedName = "hamburger";
        final var expectedValue = BigDecimal.valueOf(10).setScale(2, RoundingMode.HALF_EVEN);
        final var expectedErrorMesssage = "check the ingredientes, they can be in invalid state";
        Food food = Food.with(expectedName, expectedValue, null);

        final var expectedError = assertThrows(DomainException.class, () -> food.validate(new ThrowsErrorValidatorHandler()));

        assertNotNull(food);
        assertEquals(expectedErrorMesssage, expectedError.getMessage());
        assertEquals(expectedName, food.getName());
        assertEquals(expectedValue, food.getPrice().getValue());
    }


}