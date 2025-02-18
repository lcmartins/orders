package com.lcmartins.application.order.create;

import com.lcmartins.domain.entities.order.TransientOrderItem;

import java.util.List;
import java.util.stream.Collectors;

public record CreateOrderCommand(
        Long customerId,
        List<TransientOrderItem> items
) {
    public static CreateOrderCommand with(final Long customerId, final List<TransientOrderItem> items) {
        return new CreateOrderCommand(customerId, items);
    }

    public List<String> itemsIdList() {
        return this.items.stream().map(TransientOrderItem::id).collect(Collectors.toList());
    }
}
