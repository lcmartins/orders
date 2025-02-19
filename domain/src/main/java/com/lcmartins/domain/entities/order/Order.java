package com.lcmartins.domain.entities.order;

import com.lcmartins.domain.entities.DomainEntity;
import com.lcmartins.domain.entities.customer.Customer;
import com.lcmartins.domain.general.Price;
import com.lcmartins.domain.general.Sellable;
import com.lcmartins.domain.validators.Handler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order<T extends Sellable> extends DomainEntity {
    private OrderEntityId id;
    private final List<OrderItem<? extends Sellable>> items;
    private final Customer customer;
    private Price total;
    private final BigDecimal minOrderValue;

    public Order(final List<OrderItem<T>> items,
                 final Customer customer,
                 final BigDecimal minOrderValue,
                 final OrderEntityId id) {
        this.id = id;
        this.items = new ArrayList<>(items);
        this.customer = customer;
        this.minOrderValue = minOrderValue;
        this.total = this.calculateTotal();
    }

    public Order(final OrderItem<? extends Sellable> item,
                 final Customer customer,
                 final OrderEntityId id,
                 BigDecimal minOrderValue) {
        this.id = id;
        List<OrderItem<? extends Sellable>> orderItems = new ArrayList<>();
        orderItems.add(item);
        this.items = orderItems;
        this.customer = customer;
        this.minOrderValue = minOrderValue;
        this.total = this.calculateTotal();
    }

    private Price calculateTotal() {
        return Price.with(this.items.stream()
                .map(orderItem -> orderItem.getTotal().getValue())
                .reduce(BigDecimal.ZERO, BigDecimal::add)
        );
    }

    public void add(final OrderItem<? extends Sellable> item) {
        this.items.add(item);
        this.total = calculateTotal();
    }

    public OrderEntityId getId() {
        return id;
    }


    public void setId(OrderEntityId id) {
        this.id = id;
    }

    public BigDecimal getMinOrderValue() {
        return minOrderValue;
    }

    public List<OrderItem<? extends Sellable>> getItems() {
        return items;
    }

    public OrderItem<? extends Sellable> getFirst() {
        return this.getItems().get(0);
    }

    public Price getTotal() {
        return total;
    }

    public BigDecimal getRawTotal() {
        return total.getValue();
    }

    public Customer getCustomer() {
        return customer;
    }

    public BigDecimal getTotalItem(int i) {
        return this.items.get(i).getTotal().getValue();
    }

    @Override
    public void validate(Handler handler) {
        OrderValidator.with(handler, this.minOrderValue).validate(this);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Order<?> order)) return false;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getItems(), getCustomer(), getTotal(), minOrderValue);
    }
}
