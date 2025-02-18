package com.lcmartins.domain.entities.order;

import com.lcmartins.domain.general.Price;
import com.lcmartins.domain.general.Sellable;
import com.lcmartins.domain.validators.ThrowsErrorValidatorHandler;

import java.util.Objects;

public class OrderItem<T extends Sellable>{
    private final T item;
    private final String name;
    private final Integer quantity;
    private final Price total;
    private final Price itemPrice;

    public OrderItem(final T item, final String name, Integer quantity) {
        Objects.requireNonNull(item.getPrice(), "an item must have a price to be inside an order");
        Objects.requireNonNull(quantity, "an item must have a quantity to be ordered");
        item.validate(new ThrowsErrorValidatorHandler());
        this.item = item;
        this.name = name;
        this.itemPrice = item.getPrice();
        this.quantity = quantity;
        this.total = Price.withCalc(quantity, item.getPrice().getValue());
    }

    public T getItem() {
        return item;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Price getTotal() {
        return total;
    }

    public Price getItemPrice() {
        return itemPrice;
    }
}
