package com.lcmartins.infrastructure.configuration;

import com.lcmartins.application.order.create.BaseCreateOrderUseCase;
import com.lcmartins.application.order.create.DefaultCreateOrderUseCase;
import com.lcmartins.domain.gateways.OrderGateway;
import com.lcmartins.domain.gateways.SellableGateway;
import com.lcmartins.infrastructure.orders.persistence.food.FoodDBEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderUseCaseConfig {
    private final OrderGateway<FoodDBEntity> orderGateway;
    private final SellableGateway<FoodDBEntity> foodSellableGateway;

    public OrderUseCaseConfig(OrderGateway<FoodDBEntity> orderGateway, SellableGateway<FoodDBEntity> foodSellableGateway) {
        this.orderGateway = orderGateway;
        this.foodSellableGateway = foodSellableGateway;
    }

    @Bean
    public BaseCreateOrderUseCase createOrderUseCase() {
        return new DefaultCreateOrderUseCase<>(orderGateway, foodSellableGateway);
    }
}
