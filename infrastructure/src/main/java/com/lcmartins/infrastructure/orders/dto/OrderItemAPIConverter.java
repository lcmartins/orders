package com.lcmartins.infrastructure.orders.dto;

import com.lcmartins.domain.entities.order.TransientOrderItem;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface OrderItemAPIConverter {
    Function<List<OrderItemAPIInput>, List<TransientOrderItem>> convert = input ->
            input.stream().map(item->TransientOrderItem.with(item.itemId(), item.quantity())).collect(Collectors.toList());
}
