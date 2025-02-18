package com.lcmartins.application.order.create;

import com.lcmartins.domain.entities.customer.Customer;
import com.lcmartins.domain.entities.order.Order;
import com.lcmartins.domain.entities.order.OrderItem;
import com.lcmartins.domain.entities.order.TransientOrderItem;
import com.lcmartins.domain.exceptions.DomainException;
import com.lcmartins.domain.gateways.SellableGateway;
import com.lcmartins.domain.gateways.OrderGateway;
import com.lcmartins.domain.general.Sellable;
import com.lcmartins.domain.validators.ThrowsErrorValidatorHandler;

import java.util.ArrayList;
import java.util.List;

public class DefaultCreateOrderUseCase<T extends Sellable> extends BaseCreateOrderUseCase {
    private final OrderGateway<T> orderGateway;
    private final SellableGateway<T> sellableGateway;

    public DefaultCreateOrderUseCase(OrderGateway<T> orderGateway, SellableGateway<T> sellableGateway) {
        this.orderGateway = orderGateway;
        this.sellableGateway = sellableGateway;
    }


    public List<OrderItem<T>> fromTransient(List<TransientOrderItem> incomingItems, List<T> foods) throws DomainException {
        List<OrderItem<T>> newOrderDomainItems = new ArrayList<>();
        for (T item : foods) {
            Integer quantity = incomingItems
                    .stream()
                    .filter(orderItemDTO -> orderItemDTO.id().equals(item.getId().getValue()))
                    .map(TransientOrderItem::quantity)
                    .reduce(0, Integer::sum);

            OrderItem<T> orderItem = new OrderItem<>(item, item.getName(), quantity);
            newOrderDomainItems.add(orderItem);
        }
        return newOrderDomainItems;
    }

    @Override
    public OrderOutput execute(CreateOrderCommand command) {
        final List<T> foods = sellableGateway.getItemsByIds(command.itemsIdList());
        final var orderItems = fromTransient(command.items(), foods);
        final var order = new Order<>(orderItems, Customer.with(command.customerId()), sellableGateway.getMininumOrderValue(), null);
        order.validate(new ThrowsErrorValidatorHandler());
        final var createdOrder = orderGateway.create(order);
        return OrderOutput.with(createdOrder);
    }
}
