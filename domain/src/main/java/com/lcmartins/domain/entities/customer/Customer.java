package com.lcmartins.domain.entities.customer;

public record Customer(Long id) {
    public static Customer with(final Long id) {
        return new Customer(id);
    }
}
