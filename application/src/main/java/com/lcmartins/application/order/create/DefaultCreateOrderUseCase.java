package com.lcmartins.application.order.create;

import com.lcmartins.domain.entities.customer.Customer;
import com.lcmartins.domain.entities.order.Order;
import com.lcmartins.domain.entities.order.OrderItem;
import com.lcmartins.domain.entities.order.TransientOrderItem;
import com.lcmartins.domain.gateways.OrderGateway;
import com.lcmartins.domain.general.Sellable;
import com.lcmartins.domain.validators.ThrowsErrorValidatorHandler;

import java.util.ArrayList;
import java.util.List;

public class DefaultCreateOrderUseCase<T extends Sellable> extends BaseCreateOrderUseCase {
    private final OrderGateway<T> orderGateway;

    public DefaultCreateOrderUseCase(OrderGateway<T> orderGateway) {
        this.orderGateway = orderGateway;
    }

    @Override
    public OrderOutput execute(CreateOrderCommand command) {
        final List<T> domainItems = orderGateway.getItemsByIds(command.itemsIdList());
        final var orderItems = fromTransient(command.items(), domainItems);
        final var order = new Order<>(orderItems, Customer.with(command.customerId()), orderGateway.getMininumOrderValue(), null);
        order.validate(new ThrowsErrorValidatorHandler());
        final var createdOrder = orderGateway.create(order);
        return OrderOutput.with(createdOrder);
    }

    public List<OrderItem<T>> fromTransient(List<TransientOrderItem> transientOrderItems, List<T> domainItems) {
        List<OrderItem<T>> newItemsToOrder = new ArrayList<>();
        domainItems.forEach(domainOrderingItem -> {
            Integer quantity = transientOrderItems
                    .stream()
                    .filter(transientOrderItem -> transientOrderItem.id().equals(domainOrderingItem.getId().getValue()))
                    .map(TransientOrderItem::quantity)
                    .reduce(0, Integer::sum);
            OrderItem<T> newItemToOrder = new OrderItem<>(domainOrderingItem, quantity);
            newItemsToOrder.add(newItemToOrder);
        });
        return newItemsToOrder;
    }
}
