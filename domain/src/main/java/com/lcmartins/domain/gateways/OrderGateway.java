package com.lcmartins.domain.gateways;

import com.lcmartins.domain.entities.order.Order;
import com.lcmartins.domain.general.Sellable;

import java.math.BigDecimal;
import java.util.List;

public abstract class OrderGateway<T extends Sellable> {
    public abstract Order<T> create(Order<T> order);
    public abstract List<T> getItemsByIds(List<String> ids);
    public abstract BigDecimal getMininumOrderValue();
}
