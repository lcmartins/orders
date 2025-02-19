package com.lcmartins.infrastructure.configuration;

import com.lcmartins.domain.gateways.OrderGateway;
import com.lcmartins.infrastructure.orders.persistence.food.FoodDBEntity;
import com.lcmartins.infrastructure.orders.persistence.OrderMysqlGateway;
import com.lcmartins.infrastructure.orders.persistence.order.OrderMysqlRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

@Configuration
public class OrderFoodGatewayConfig {
    private final JpaRepository<FoodDBEntity, String> foodDDMysqlRepository;
    private final OrderMysqlRepository orderDBMysqlRepository;

    public OrderFoodGatewayConfig(JpaRepository<FoodDBEntity, String> foodDDMysqlRepository, OrderMysqlRepository orderDBMysqlRepository) {
        this.foodDDMysqlRepository = foodDDMysqlRepository;
        this.orderDBMysqlRepository = orderDBMysqlRepository;
    }


    @Bean
    public OrderGateway<FoodDBEntity> orderGateway() {
        return new OrderMysqlGateway<>(foodDDMysqlRepository, orderDBMysqlRepository);
    }
}
