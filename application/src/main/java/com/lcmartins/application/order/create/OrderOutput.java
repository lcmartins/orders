package com.lcmartins.application.order.create;

import com.lcmartins.domain.entities.customer.Customer;
import com.lcmartins.domain.entities.order.Order;
import com.lcmartins.domain.general.Sellable;

import java.math.BigDecimal;
import java.util.List;

public record OrderOutput(
    String id,
    List<OrderItemOutput> items,
    Customer customer,
    BigDecimal total,
    BigDecimal minOrderValue
) {
    public static <T extends Sellable> OrderOutput with(final Order<T> order) {
        final var orderItems = order.getItems().stream().map(OrderItemOutput::with).toList();
        return new OrderOutput(order.getId().id, orderItems, order.getCustomer(), order.getTotal().getValue(), order.getMinOrderValue());
    }
}
