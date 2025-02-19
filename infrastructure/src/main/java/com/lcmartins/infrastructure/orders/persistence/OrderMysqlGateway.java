package com.lcmartins.infrastructure.orders.persistence;

import com.lcmartins.domain.entities.order.Order;
import com.lcmartins.domain.entities.order.OrderEntityId;
import com.lcmartins.domain.entities.order.OrderItem;
import com.lcmartins.domain.gateways.OrderGateway;
import com.lcmartins.domain.general.Sellable;
import com.lcmartins.infrastructure.orders.persistence.order.OrderDBEntity;
import com.lcmartins.infrastructure.orders.persistence.order.OrderItemDBEntity;
import com.lcmartins.infrastructure.orders.persistence.order.OrderMysqlRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderMysqlGateway<T extends Sellable> extends OrderGateway<T> {
    private final JpaRepository<T, String> itemMysqlRepository;
    private final OrderMysqlRepository orderMysqlRepository;

    public OrderMysqlGateway(JpaRepository<T, String> mysqlRepository, OrderMysqlRepository orderMysqlRepository) {
        this.itemMysqlRepository = mysqlRepository;
        this.orderMysqlRepository = orderMysqlRepository;
    }


    @Transactional
    @Override
    public Order<T> create(Order<T> order) {
        List<T> itemsById = this.itemMysqlRepository
                .findAllById(order.getItems().stream().map(i -> i.getItem().getId().getValue())
                        .collect(Collectors.toList()));
        OrderDBEntity orderDBEntity = new OrderDBEntity(UUID.randomUUID().toString(), order.getCustomer().id(), order.getRawTotal());
        List<OrderItemDBEntity> orderItemDBEntities = new ArrayList<>();
        itemsById.forEach(item -> {
            final OrderItem<? extends Sellable> originalOrderItem = order.getItems().stream()
                    .filter(orderItem -> orderItem.getItem().getId().getValue().equals(item.getId().getValue()))
                    .findFirst().orElseThrow();
            OrderItemDBEntity newItem = new OrderItemDBEntity(item.getName(), item.getPrice().getValue(), originalOrderItem.getQuantity(), orderDBEntity);
            orderItemDBEntities.add(newItem);
        });
        orderDBEntity.setOrderItemEntities(orderItemDBEntities);
        final var savedOrder = orderMysqlRepository.save(orderDBEntity);
        order.setId(OrderEntityId.with(savedOrder.getId()));
        return order;
    }

    @Override
    public List<T> getItemsByIds(List<String> ids) {
        return this.itemMysqlRepository.findAllById(ids);
    }

    @Override
    public BigDecimal getMininumOrderValue() {
        return BigDecimal.valueOf(100); //TODO pegar de configuration;
    }
}
