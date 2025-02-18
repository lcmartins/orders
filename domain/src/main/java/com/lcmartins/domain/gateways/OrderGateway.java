package com.lcmartins.domain.gateways;

import com.lcmartins.domain.entities.order.Order;
import com.lcmartins.domain.general.Sellable;

import java.util.Optional;

public abstract class OrderGateway<T extends Sellable> {
    public abstract Order<T> create(Order<T> order);
    public abstract void delete(String id);
    public abstract Optional<Order<T>> find(String id);
}
