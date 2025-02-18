package com.lcmartins.infrastructure.orders.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OrderItemAPIInput(
        @JsonProperty("item_id") String itemId,
        @JsonProperty("quantity") Integer quantity
) {
}
