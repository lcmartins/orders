package com.lcmartins.infrastructure.orders.persistence.order;

import com.lcmartins.domain.entities.order.Order;
import com.lcmartins.domain.entities.order.OrderEntityId;
import com.lcmartins.domain.gateways.OrderGateway;
import com.lcmartins.domain.general.Sellable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderMysqlGateway<T extends Sellable> extends OrderGateway<T> {
    private final JpaRepository<T, String> itemMysqlRepository;
    private final OrderMysqlRepository orderMysqlRepository;

    public OrderMysqlGateway(JpaRepository<T, String> mysqlRepository, OrderMysqlRepository orderMysqlRepository) {
        this.itemMysqlRepository = mysqlRepository;
        this.orderMysqlRepository = orderMysqlRepository;
    }

    @Override
    public Order<T> create(Order<T> order) {
        List<T> itemsById = this.itemMysqlRepository
                .findAllById(order.getItems().stream().map(i -> i.getItem().getId().getValue())
                        .collect(Collectors.toList()));
        OrderDBEntity orderItemDBEntity = new OrderDBEntity(UUID.randomUUID().toString(), order.getCustomer().id(), order.getRawTotal());
        List<OrderItemDBEntity> itemDBEntities = new ArrayList<>();
        itemsById.forEach(item -> {
            OrderItemDBEntity newItem = new OrderItemDBEntity(item.getName(), item.getPrice().getValue(), orderItemDBEntity);
            itemDBEntities.add(newItem);
        });
        orderItemDBEntity.setOrderItemEntities(itemDBEntities);
        final var savedOrder = orderMysqlRepository.save(orderItemDBEntity);
        order.setId(OrderEntityId.with(savedOrder.getId()));
        return order;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Optional<Order<T>> find(String id) {
        return Optional.empty();
    }
}
