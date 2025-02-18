package com.lcmartins.application.order.create;

import com.lcmartins.domain.entities.order.OrderItem;

import java.math.BigDecimal;

public record OrderItemOutput(
        String name,
        Integer quantity,
        BigDecimal itemPrice,
        BigDecimal total
) {
    public static OrderItemOutput with(OrderItem<?> orderItem) {
        return new OrderItemOutput(
                orderItem.getName(),
                orderItem.getQuantity(),
                orderItem.getItemPrice().getValue(),
                orderItem.getTotal().getValue()
        );
    }
}
