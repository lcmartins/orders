package com.lcmartins.orders;

import com.lcmartins.application.order.create.CreateOrderCommand;
import com.lcmartins.application.order.create.DefaultCreateOrderUseCase;
import com.lcmartins.domain.entities.customer.Customer;
import com.lcmartins.domain.entities.food.Food;
import com.lcmartins.domain.entities.food.FoodEntityId;
import com.lcmartins.domain.entities.food.Ingredient;
import com.lcmartins.domain.entities.order.Order;
import com.lcmartins.domain.entities.order.OrderEntityId;
import com.lcmartins.domain.entities.order.OrderItem;
import com.lcmartins.domain.entities.order.TransientOrderItem;
import com.lcmartins.domain.exceptions.DomainException;
import com.lcmartins.domain.gateways.OrderGateway;
import com.lcmartins.domain.general.Price;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DefaultOrderFoodUseCaseTest {
    @InjectMocks
    private DefaultCreateOrderUseCase<Food> useCase;

    @Mock
    private OrderGateway<Food> orderGateway;


    @Test
    public void givenAValidFood_whenCallsToCreateOrder_thenShouldCrateOrderAsRequired() {
        final var expectedNameFood1 = "Hamburger";
        final var expectedPrice1 = Price.with(1000L).getValue();
        final var expectedIngredients1 = new ArrayList<Ingredient>();
        expectedIngredients1.add(Ingredient.with("pao", 10));
        expectedIngredients1.add(Ingredient.with("hamburger", 10));
        expectedIngredients1.add(Ingredient.with("folha alfaçe", 10));
        expectedIngredients1.add(Ingredient.with("rodela tomate", 10));

        final var orderingFood1 = Food.with(expectedNameFood1, expectedPrice1, expectedIngredients1, FoodEntityId.with("1L"));

        final var expectedNameFood2 = "Hmburger australizano";
        final var expectedPrice2 = Price.with(1000L).getValue();
        final var expectedIngredients2 = new ArrayList<Ingredient>();
        expectedIngredients2.add(Ingredient.with("pao australiano", 10));
        expectedIngredients2.add(Ingredient.with("hamburger", 10));
        expectedIngredients2.add(Ingredient.with("folha alfaçe", 10));
        expectedIngredients2.add(Ingredient.with("rodela tomate", 10));
        expectedIngredients2.add(Ingredient.with("maionese temperada", 10));

        final var orderingFood2 = Food.with(expectedNameFood2, expectedPrice2, expectedIngredients2, FoodEntityId.with("2L"));


        final var createOrderCommand = CreateOrderCommand.with(1L,
                List.of(TransientOrderItem.with("1L", 10),
                        TransientOrderItem.with("2L", 3),
                        TransientOrderItem.with("1L", 2))
        );
        List<OrderItem<Food>> newOrderDomainItems = new ArrayList<>();
        newOrderDomainItems.add(new OrderItem<>(orderingFood1, orderingFood1.getName(), 12));
        newOrderDomainItems.add(new OrderItem<>(orderingFood2, orderingFood2.getName(), 3));


        when(orderGateway.getItemsByIds(anyList())).thenReturn(List.of(orderingFood1, orderingFood2));
        when(orderGateway.getMininumOrderValue()).thenReturn(BigDecimal.valueOf(15000));
        when(orderGateway.create(any())).thenReturn(
                new Order<>(newOrderDomainItems,
                        Customer.with(1L),
                        BigDecimal.valueOf(1000L),
                        OrderEntityId.with(UUID.randomUUID().toString())
                )
        );
        final var output = useCase.execute(createOrderCommand);

        verify(orderGateway, times(1)).getItemsByIds(any());
        verify(orderGateway, times(1)).create(any());
        assertNotNull(output.id());
        assertEquals(2, output.items().size());
        assertEquals(Price.with(15000L).getValue(), output.total());
    }

    @Test
    public void givenAValidFood_whenCallsToCreateOrderWithoutMinValue_thenThrowException() {
        final var expectedNameFood1 = "Hamburger";
        final var expectedPrice1 = Price.with(1000L).getValue();
        final var expectedIngredients1 = new ArrayList<Ingredient>();
        expectedIngredients1.add(Ingredient.with("pao", 10));
        expectedIngredients1.add(Ingredient.with("hamburger", 10));
        expectedIngredients1.add(Ingredient.with("folha alfaçe", 10));
        expectedIngredients1.add(Ingredient.with("rodela tomate", 10));

        final var orderingFood1 = Food.with(expectedNameFood1, expectedPrice1, expectedIngredients1, FoodEntityId.with("1L"));

        final var expectedNameFood2 = "Hmburger australizano";
        final var expectedPrice2 = Price.with(1000L).getValue();
        final var expectedIngredients2 = new ArrayList<Ingredient>();
        expectedIngredients2.add(Ingredient.with("pao australiano", 10));
        expectedIngredients2.add(Ingredient.with("hamburger", 10));
        expectedIngredients2.add(Ingredient.with("folha alfaçe", 10));
        expectedIngredients2.add(Ingredient.with("rodela tomate", 10));
        expectedIngredients2.add(Ingredient.with("maionese temperada", 10));

        final var orderingFood2 = Food.with(expectedNameFood2, expectedPrice2, expectedIngredients2, FoodEntityId.with("2L"));


        final var createOrderCommand = CreateOrderCommand.with(1L,
                List.of(TransientOrderItem.with("1L", 10),
                        TransientOrderItem.with("2L", 3),
                        TransientOrderItem.with("1L", 2))
        );

        when(orderGateway.getItemsByIds(anyList())).thenReturn(List.of(orderingFood1, orderingFood2));
        when(orderGateway.getMininumOrderValue()).thenReturn(BigDecimal.valueOf(20000));

        final var exception = assertThrows(DomainException.class, () -> useCase.execute(createOrderCommand));

        assertEquals(exception.getMessage(), "value 15000.00 is less than minimum order value tha is 20000");
    }
}
