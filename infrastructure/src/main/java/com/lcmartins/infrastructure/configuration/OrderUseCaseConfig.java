package com.lcmartins.infrastructure.configuration;

import com.lcmartins.application.order.create.BaseCreateOrderUseCase;
import com.lcmartins.application.order.create.DefaultCreateOrderUseCase;
import com.lcmartins.domain.gateways.OrderGateway;
import com.lcmartins.infrastructure.orders.persistence.food.FoodDBEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderUseCaseConfig {
    private final OrderGateway<FoodDBEntity> orderGateway;

    public OrderUseCaseConfig(OrderGateway<FoodDBEntity> orderGateway) {
        this.orderGateway = orderGateway;
    }

    @Bean
    public BaseCreateOrderUseCase createOrderUseCase() {
        return new DefaultCreateOrderUseCase<>(orderGateway);
    }
}
