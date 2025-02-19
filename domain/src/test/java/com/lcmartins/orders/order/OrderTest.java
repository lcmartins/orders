package com.lcmartins.orders.order;

import com.lcmartins.domain.entities.customer.Customer;
import com.lcmartins.domain.entities.food.Food;
import com.lcmartins.domain.entities.food.FoodEntityId;
import com.lcmartins.domain.entities.food.Ingredient;
import com.lcmartins.domain.entities.order.Order;
import com.lcmartins.domain.entities.order.OrderItem;
import com.lcmartins.domain.exceptions.DomainException;
import com.lcmartins.domain.validators.ThrowsErrorValidatorHandler;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    @Test
    public void givenAValidFood_whenCallsNewOrder_thenAnOrderShouldBeCreatedCorrectly() {
        final var expectedNameFirstItem = "hamburger";
        final var expectedNameSecondItem = "hamburger 2";
        final var expectedPriceFirstItem = BigDecimal.valueOf(100.33899).setScale(2, RoundingMode.HALF_EVEN);
        final var expectedPriceSecondItem = BigDecimal.valueOf(50.50).setScale(2, RoundingMode.HALF_EVEN);
        final var expectedIngredientName = "pao";
        final var expectedQuantityInStock = 10;
        final var expectedQuantityToBuy = 10;
        var expectedCustomerId = 10L;

        final List<Ingredient> expectedIngredient = new ArrayList<>();
        final var expectedTotal = BigDecimal.valueOf(expectedQuantityToBuy).multiply(expectedPriceFirstItem);
        expectedIngredient.add(Ingredient.with(expectedIngredientName, expectedQuantityInStock));
        Food food = Food.with(expectedNameFirstItem, expectedPriceFirstItem, expectedIngredient);


        final List<Ingredient> expectedIngredientItem2 = new ArrayList<>();
        final var expectedTotal2 = BigDecimal.valueOf(expectedQuantityToBuy).multiply(expectedPriceSecondItem);
        expectedIngredientItem2.add(Ingredient.with(expectedNameSecondItem, expectedQuantityInStock));
        Food food2 = Food.with(expectedNameSecondItem, expectedPriceSecondItem, expectedIngredientItem2);

        final Order<Food> order = new Order<>(
                new OrderItem<>(
                        food,
                        10),
                Customer.with(expectedCustomerId),
                null,
                BigDecimal.ONE
        );
        order.add(new OrderItem<>(food2, 10));

        assertNotNull(order);
        assertEquals(expectedTotal.add(expectedTotal2), order.getRawTotal());
        assertEquals(expectedTotal, order.getTotalItem(0));
        assertEquals(expectedTotal2, order.getTotalItem(1));

        assertEquals(expectedNameFirstItem, order.getItems().get(0).getName());
        assertEquals(expectedNameSecondItem, order.getItems().get(1).getName());
        assertEquals(expectedPriceFirstItem, order.getItems().get(0).getItem().getPrice().getValue());
        assertEquals(expectedPriceSecondItem, order.getItems().get(1).getItem().getPrice().getValue());
        assertEquals(expectedCustomerId, order.getCustomer().id());
    }

    @Test
    public void givenAValidFoods_whenCallsAdd_thenCorrectValueShouldBeAddedToTotal() {
        final var expectedName = "hamburger 2";
        final var expectedPrice = BigDecimal.valueOf(50.50).setScale(2, RoundingMode.HALF_EVEN);
        final var expectedQuantityInStock = 10;
        final var expectedQuantityToBuy = 10;
        final var expectedTotal = BigDecimal.valueOf(expectedQuantityToBuy).multiply(expectedPrice).multiply(BigDecimal.valueOf(2));
        var expectedCustomerId = 10L;

        final List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(Ingredient.with(expectedName, expectedQuantityInStock));
        Food hamburger = Food.with(expectedName, expectedPrice, ingredients);

        final Order<Food> order = new Order<>(
                new OrderItem<>(
                        hamburger,
                        10),
                Customer.with(expectedCustomerId),
                null,
                BigDecimal.ONE
        );
        order.add(new OrderItem<>(hamburger, 10));
        assertNotNull(order);
        assertEquals(expectedTotal, order.getRawTotal());
        assertEquals(expectedName, order.getFirst().getName());
        assertEquals(expectedPrice, order.getFirst().getItem().getPrice().getValue());
        assertEquals(expectedCustomerId, order.getCustomer().id());
        assertNull(hamburger.getId());
    }

    @Test
    public void givenAValidFoodAndId_whenCallsCreatre_thenIdShouldBeReturned() {
        final var expectedName = "hamburger 2";
        final var expectedPrice = BigDecimal.valueOf(50.50).setScale(2, RoundingMode.HALF_EVEN);
        final var expectedQuantityInStock = 10;
        final var expectedQuantityToBuy = 10;
        final var expectedTotal = BigDecimal.valueOf(expectedQuantityToBuy).multiply(expectedPrice).multiply(BigDecimal.valueOf(2));
        var expectedCustomerId = 10L;
        var expectedId = FoodEntityId.with("10");

        final List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(Ingredient.with(expectedName, expectedQuantityInStock));
        Food hamburger = Food.with(expectedName, expectedPrice, ingredients, expectedId);

        final Order<Food> order = new Order<>(
                new OrderItem<>(
                        hamburger,
                        10),
                Customer.with(expectedCustomerId),
                null,
                BigDecimal.ONE
        );
        order.add(new OrderItem<>(hamburger, 10));
        assertNotNull(order);
        assertEquals(expectedTotal, order.getRawTotal());
        assertEquals(expectedName, order.getFirst().getName());
        assertEquals(expectedPrice, order.getFirst().getItem().getPrice().getValue());
        assertEquals(expectedCustomerId, order.getCustomer().id());
        assertEquals(expectedId.getValue(), hamburger.getId().getValue());
    }

    @Test
    public void givenAValidFoodButMinValue_whenCallsCreatre_thenIdShouldThrow() {
        final var expectedName = "hamburger 2";
        final var expectedPrice = BigDecimal.valueOf(50.50).setScale(2, RoundingMode.HALF_EVEN);
        final var expectedQuantityInStock = 10;
        var expectedCustomerId = 10L;
        var expectedId = FoodEntityId.with("10");
        final var expectedErrorMessage = "value 1010.00 is less than minimum order value tha is 10000";
        final List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(Ingredient.with(expectedName, expectedQuantityInStock));
        Food hamburger = Food.with(expectedName, expectedPrice, ingredients, expectedId);

        final Order<Food> order = new Order<>(
                new OrderItem<>(
                        hamburger,
                        10),
                Customer.with(expectedCustomerId),
                null,
                BigDecimal.valueOf(10000L)
        );
        order.add(new OrderItem<>(hamburger, 10));
        final var expectedException = assertThrows(DomainException.class, () -> order.validate(new ThrowsErrorValidatorHandler()));

        assertNotNull(order);
        assertEquals(expectedErrorMessage, expectedException.getMessage());
    }

}
