package com.lcmartins.domain.entities.order;

import com.lcmartins.domain.validators.DomainError;
import com.lcmartins.domain.validators.Handler;
import com.lcmartins.domain.validators.Validator;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderValidator extends Validator<Order> {
    private final BigDecimal minOrderValue;

    private OrderValidator(Handler handler, final BigDecimal minOrderValue) {
        super(handler);
        this.minOrderValue = minOrderValue;
    }

    public static OrderValidator with(final Handler handler, final BigDecimal minOrderValue) {
        Objects.requireNonNull(minOrderValue);
        return new OrderValidator(handler, minOrderValue);
    }

    @Override
    public void validate(Order order) {
        if (order.getTotal().isLessThan(this.minOrderValue)) {
            getHandler().add(
                    new DomainError(
                            "value %s is less than minimum order value tha is %s".formatted(order.getTotal().getValue(), this.minOrderValue)
                    )
            );
        }
    }
}
