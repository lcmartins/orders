package com.lcmartins.infrastructure.orders.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record OrderAPIInput(
        @JsonProperty("customer_id") Long customerId,
        @JsonProperty("items") List<OrderItemAPIInput> items
) {
}
